package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;

@FacesComponent(value = "neotech.bootstrapfaces.breadcrumbItem")
public class BreadcrumbItem extends MenuItem {

    public enum PropertyKeys {
        icon
    };

    @Override
    public void setIcon(String icon) {
        getStateHelper().put(PropertyKeys.icon, icon);
    }

    @Override
    public String getIcon() {
        return (String)getStateHelper().eval(PropertyKeys.icon, null);
    }

}