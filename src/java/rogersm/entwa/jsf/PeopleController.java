package rogersm.entwa.jsf;

import rogersm.entwa.entities.People;
import rogersm.entwa.jsf.util.JsfUtil;
import rogersm.entwa.jsf.util.PaginationHelper;
import rogersm.entwa.beans.PeopleFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "peopleController")
@SessionScoped
public class PeopleController implements Serializable {

    private People current;
    private DataModel items = null;
    @EJB
    private rogersm.entwa.beans.PeopleFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PeopleController() {
    }

    public People getSelected() {
        if (current == null) {
            current = new People();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PeopleFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }

                @Override
                public DataModel searchPageDataModel(String search) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/people/List?faces-redirect=true";
    }

    public String prepareView() {
        current = (People) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/people/View?faces-redirect=true";
    }

    public String prepareCreate() {
        current = new People();
        selectedItemIndex = -1;
        return "/people/Create?faces-redirect=true";
    }
    
    public String prepareAccount() {
        return "/people/account?faces-redirect=true";
    }

    public String create() {
        try {
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PeopleCreated"));
            return prepareAccount();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (People) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/people/Edit?faces-redirect=true";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PeopleUpdated"));
            return "/people/View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (People) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/people/List?faces-redirect=true";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/people/View?faces-redirect=true";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/people/List?faces-redirect=true";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PeopleDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "/people/List?faces-redirect=true";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/people/List?faces-redirect=true";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public SelectItem[] getByTypeAvailableSelectOne(String type) {
        return JsfUtil.getSelectItems(ejbFacade.findByType(type), true);
    }
    
    public SelectItem[] getByTypeAvailableWithUserSelected() {
        return JsfUtil.getSelectItems(ejbFacade.findById(current.getId()), false);
    }
    
    /*
     * http://stackoverflow.com/questions/2206911/best-way-for-user-authentication-on-javaee-6-using-jsf-2-0/2207147#2207147
     * https://docs.jboss.org/webbeans/reference/current/en-US/html/example.html
     */
    private String email;
    private String password;
    private List <People> results;
    
    public String login(){ 
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try{
            results = ejbFacade.findByEmailAndPassword(current.getEmail(), current.getPassword());
        }catch(Exception e){
            return "";
        }
        
        if ( !results.isEmpty() ) {
            current = results.get(0);
            externalContext.getSessionMap().put("id", current.getId());
            externalContext.getSessionMap().put("email", current.getEmail());
            externalContext.getSessionMap().put("name", current.getName());
            return "/people/account?faces-redirect=true";
        }
        return "";
    }
    
    public String logout(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        current = null;
        return "/people/logout?faces-redirect=true";
    }
     
    @FacesConverter(forClass = People.class)
    public static class PeopleControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PeopleController controller = (PeopleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "peopleController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof People) {
                People o = (People) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + People.class.getName());
            }
        }
    }
}
