package br.com.neotech.bootstrapfaces.component;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import br.com.neotech.framework.lazy.PaginatedList;

@FacesComponent("neotech.bootstrapfaces.paginatedtable")
public class PaginatedTable extends UINamingContainer {

    enum PropertyKeys {
        pageSizes, pageSize, columnClasses, value, searchParameter, jsId, renderOnSearch
    }

    private Integer navStart;
    private Integer navEnd;

    public void definePageSize() {
        if(getValue() != null) {
            ((PaginatedList<?>)getValue()).setPageSize(getPageSize());
        }
    }

    public int[] getPageSelectorList() {
        String[] temp = getPageSizes().split(" ");
        int[] saida = new int[temp.length];
        for(int i = 0; i < temp.length; i++) {
            saida[i] = Integer.parseInt(temp[i]);
        }
        return saida;
    }

    public boolean getShowNavigation() {
        if(getValue() != null) {
            PaginatedList<?> pl = (PaginatedList<?>)getValue();
            return pl.getTotalSize() > pl.getPageSize();
        }
        return false;
    }

    public int getPageStart() {
        if(getValue() != null) {
            PaginatedList<?> pl = (PaginatedList<?>)getValue();
            return pl.getPageIndex() * pl.getPageSize() - pl.getPageSize() +1;
        }
        return 0;
    }

    public int getPageEnd() {
        if(getValue() != null) {
            PaginatedList<?> pl = (PaginatedList<?>)getValue();
            int fim = pl.getPageSize();
            if(pl.size() < pl.getPageSize()) {
                fim = pl.size();
            }
            return getPageStart() + fim -1;
        }
        return 0;
    }

    public int getResultSetCount() {
        if(getValue() != null) {
            PaginatedList<?> pl = (PaginatedList<?>)getValue();
            return pl.getTotalSize();
        }
        return 0;
    }

    public int getPageIndex() {
        if(getValue() != null) {
            return ((PaginatedList<?>)getValue()).getPageIndex();
        }
        return 1;
    }

    public List<Integer> getNavigationPages() {
        calculateNavigatorPages();
        List<Integer> saida = new ArrayList<Integer>();
        for(int i = navStart; i <= navEnd; i++) {
            saida.add(i);
        }
        return saida;
    }

    public void calculateNavigatorPages() {
        if(getValue() != null) {
            int lastPage = ((PaginatedList<?>)getValue()).getNumberOfPages() -1;
            int currentPage = ((PaginatedList<?>)getValue()).getPageIndex() - 1;
            navStart = currentPage - 4;
            navStart = navStart < 0 ? 0 : navStart;
            navEnd = currentPage + 4;
            navEnd = navEnd > lastPage ? lastPage : navEnd;
        } else {
            navStart = 0;
            navEnd = 0;
        }
    }

    public void gotoPage(int pageNumber) {
        if(getValue() != null) {
            ((PaginatedList<?>)getValue()).gotoPage(pageNumber);
        }
    }

    public void gotoFirstPage() {
        if(getValue() != null) {
            ((PaginatedList<?>)getValue()).gotoFirstPage();
        }
    }

    public void gotoLastPage() {
        if(getValue() != null) {
            ((PaginatedList<?>)getValue()).gotoLastPage();
        }
    }

    public boolean isFirstPage() {
        if(getValue() != null) {
            return ((PaginatedList<?>)getValue()).isFirstPage();
        }
        return false;
    }

    public boolean isLastPage() {
        if(getValue() != null) {
            return ((PaginatedList<?>)getValue()).isLastPage();
        }
        return false;
    }

    // -- ATRIBUTOS

    public List<?> getValue() {
        return (List<?>)getStateHelper().eval(PropertyKeys.value, null);
    }

    public void setValue(List<?> value) {
        getStateHelper().put(PropertyKeys.value, value);
    }

    public String getRenderOnSearch() {
        return (String)getStateHelper().eval(PropertyKeys.renderOnSearch, null);
    }

    public void setRenderOnSearch(String renderOnSearch) {
        getStateHelper().put(PropertyKeys.renderOnSearch, renderOnSearch);
    }

    public String getSearchParameter() {
        return (String)getStateHelper().eval(PropertyKeys.searchParameter, null);
    }

    public void setSearchParameter(String searchParameter) {
        getStateHelper().put(PropertyKeys.searchParameter, searchParameter);
    }

    public Integer getPageSize() {
        return (Integer)getStateHelper().eval(PropertyKeys.pageSize, 10);
    }

    public void setPageSize(Integer pageSize) {
        getStateHelper().put(PropertyKeys.pageSize, pageSize);
    }

    public String getPageSizes() {
        return (String)getStateHelper().eval(PropertyKeys.pageSizes, "");
    }

    public void setPageSizes(String pageSizes) {
        getStateHelper().put(PropertyKeys.pageSizes, pageSizes);
    }

    public String getJsId() {
        return (String)getStateHelper().eval(PropertyKeys.jsId, null);
    }

    public void setJsId(String jsId) {
        getStateHelper().put(PropertyKeys.jsId, jsId);
    }

}