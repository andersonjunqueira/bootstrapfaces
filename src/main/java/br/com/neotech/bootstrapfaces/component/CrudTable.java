package br.com.neotech.bootstrapfaces.component;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import br.com.neotech.framework.lazy.PaginatedList;

@FacesComponent("neotech.bootstrapfaces.crudtable")
public class CrudTable extends UINamingContainer {

    enum PropertyKeys {
        headerTitle, helpContext, pageSizes, pageSize, columnClasses, value, searchParameter, jsId, theme,
        renderOnSearch, enableNew
    }

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

    // -- ATRIBUTOS

    public Boolean getEnableNew() {
        return (Boolean)getStateHelper().eval(PropertyKeys.enableNew, false);
    }

    public void setEnableNew(Boolean value) {
        getStateHelper().put(PropertyKeys.enableNew, value);
    }

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

    public String getHeaderTitle() {
        return (String)getStateHelper().eval(PropertyKeys.headerTitle, "");
    }

    public void setHeaderTitle(String headerTitle) {
        getStateHelper().put(PropertyKeys.headerTitle, headerTitle);
    }

    public String getHelpContext() {
        return (String)getStateHelper().eval(PropertyKeys.helpContext, null);
    }

    public void setHelpContext(String helpContext) {
        getStateHelper().put(PropertyKeys.helpContext, helpContext);
    }

    public String getJsId() {
        return (String)getStateHelper().eval(PropertyKeys.jsId, null);
    }

    public void setJsId(String jsId) {
        getStateHelper().put(PropertyKeys.jsId, jsId);
    }

    public String getTheme() {
        return (String)getStateHelper().eval(PropertyKeys.theme, "primary");
    }

    public void setTheme(String theme) {
        getStateHelper().put(PropertyKeys.theme, theme);
    }

}