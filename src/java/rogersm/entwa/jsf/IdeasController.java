package rogersm.entwa.jsf;

import rogersm.entwa.entities.Ideas;
import rogersm.entwa.jsf.util.JsfUtil;
import rogersm.entwa.jsf.util.PaginationHelper;
import rogersm.entwa.beans.IdeasFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author MikeRogers
 */
@ManagedBean(name = "ideasController")
@SessionScoped
public class IdeasController implements Serializable {

    private Ideas current;
    private DataModel items = null;
    @EJB
    private rogersm.entwa.beans.IdeasFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     *
     */
    public IdeasController() {
    }

    /**
     *
     * @return
     */
    public Ideas getSelected() {
        if (current == null) {
            current = new Ideas();
            selectedItemIndex = -1;
        }
        return current;
    }

    private IdeasFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @return
     */
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
                    return new ListDataModel(getFacade().searchByTitle(search));
                }
                
                @Override
                public DataModel findPersonPageDataModel(Integer id) {
                    return new ListDataModel(getFacade().findByPerson(id));
                }
            };
        }
        return pagination;
    }

    /**
     *
     * @return
     */
    public String prepareView() {
        current = (Ideas) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/ideas/View?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String prepareCreate() {
        current = new Ideas();
        selectedItemIndex = -1;
        return "/ideas/Create?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IdeasCreated"));
            return "/ideas/View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String prepareEdit() {
        current = (Ideas) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/ideas/Edit?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IdeasUpdated"));
            return "/ideas/View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String destroy() {
        current = (Ideas) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/people/account?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/people/account?faces-redirect=true";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/people/account?faces-redirect=true";
        }
    }
    
    /*
     * The search stuff
     * 
     */
    /**
     *
     * @return
     */
    public String search(){
        this.items = null;
        return "/index?faces-redirect=true";
    }
    
    private String search = null;
    
    /**
     *
     * @return
     */
    public String getSearch() {
        return search;
    }

    /**
     *
     * @param search
     */
    public void setSearch(String search) {
        if(!search.equals("")){
            this.search = search;
        } else {
            this.search = null;
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IdeasDeleted"));
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

    /**
     *
     * @return
     */
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        if (search != null) {
            items = getPagination().searchPageDataModel(search);
        }
        return items;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public DataModel items(Integer id) {
        search = null;
        items = getPagination().findPersonPageDataModel(id);
            
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    /**
     *
     * @return
     */
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "/people/account";
    }

    /**
     *
     * @return
     */
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/people/account";
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    

    /**
     *
     */
    @FacesConverter(forClass = Ideas.class)
    public static class IdeasControllerConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IdeasController controller = (IdeasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ideasController");
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

        /**
         *
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Ideas) {
                Ideas o = (Ideas) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Ideas.class.getName());
            }
        }
    }
}
