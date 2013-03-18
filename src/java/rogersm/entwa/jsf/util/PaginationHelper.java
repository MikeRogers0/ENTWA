package rogersm.entwa.jsf.util;

import javax.faces.model.DataModel;

/**
 *
 * @author 447955
 */
public abstract class PaginationHelper {

    private int pageSize;
    private int page;

    /**
     *
     * @param pageSize
     */
    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * @return
     */
    public abstract int getItemsCount();

    /**
     *
     * @return
     */
    public abstract DataModel createPageDataModel();
    /**
     *
     * Allows models to be searched.
     * 
     * @param search
     * @return
     */
    public abstract DataModel searchPageDataModel(String search);
    /**
     *
     * Finds model by person assoicated with it.
     * 
     * @param id
     * @return
     */
    public abstract DataModel findPersonPageDataModel(Integer id);

    /**
     *
     * @return
     */
    public int getPageFirstItem() {
        return page * pageSize;
    }

    /**
     *
     * @return
     */
    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    /**
     *
     * @return
     */
    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    /**
     *
     */
    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    /**
     *
     * @return
     */
    public boolean isHasPreviousPage() {
        return page > 0;
    }

    /**
     *
     */
    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    /**
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }
}
