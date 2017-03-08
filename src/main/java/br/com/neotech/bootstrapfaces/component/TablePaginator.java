package br.com.neotech.bootstrapfaces.component;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import br.com.neotech.framework.lazy.PaginatedList;

@FacesComponent("neotech.bootstrapfaces.tablePaginator")
public class TablePaginator extends UINamingContainer {

    enum PropertyKeys {
        value, renderTarget
    }

    private Integer navStart;
    private Integer navEnd;

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

    public String getRenderTarget() {
        return (String)getStateHelper().eval(PropertyKeys.renderTarget, null);
    }

    public void setRenderTarget(String renderTarget) {
        getStateHelper().put(PropertyKeys.renderTarget, renderTarget);
    }

}