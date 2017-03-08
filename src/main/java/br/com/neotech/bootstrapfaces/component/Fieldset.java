package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value = "neotech.bootstrapfaces.fieldset")
public class Fieldset extends UINamingContainer {

    //TODO [BOOTSTRAPFACES] ADICIONAR O ENDEREÇO PADRÃO DO MANUAL PARA CONCATENAÇÃO NO LINK DO HELP

    enum PropertyKeys {
        legend, helpContext, contentPadding, headerClass, theme
    }

    public String getTheme() {
        return (String)getStateHelper().eval(PropertyKeys.theme, "primary");
    }

    public void setTheme(String theme) {
        getStateHelper().put(PropertyKeys.theme, theme);
    }

    public String getHeaderClass() {
        return (String)getStateHelper().eval(PropertyKeys.headerClass, "");
    }

    public void setHeaderClass(String headerClass) {
        getStateHelper().put(PropertyKeys.headerClass, headerClass);
    }

    public Boolean getContentPadding() {
        return (Boolean)getStateHelper().eval(PropertyKeys.contentPadding, true);
    }

    public void setContentPadding(Boolean contentPadding) {
        getStateHelper().put(PropertyKeys.contentPadding, contentPadding);
    }

    public String getHelpContext() {
        return (String)getStateHelper().eval(PropertyKeys.helpContext, null);
    }

    public void setHelpContext(String helpContext) {
        getStateHelper().put(PropertyKeys.helpContext, helpContext);
    }

    public String getLegend() {
        return (String)getStateHelper().eval(PropertyKeys.legend, null);
    }

    public void setLegend(String legend) {
        getStateHelper().put(PropertyKeys.legend, legend);
    }

}