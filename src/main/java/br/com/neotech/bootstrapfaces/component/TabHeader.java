package br.com.neotech.bootstrapfaces.component;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("neotech.bootstrapfaces.tabHeader")
public class TabHeader extends UINamingContainer {

    enum PropertyKeys {
        text, icon, active, jsId
    }

    public String getText() {
        return (String)getStateHelper().eval(PropertyKeys.text, null);
    }

    public void setText(String text) {
        getStateHelper().put(PropertyKeys.text, text);
    }

    public String getIcon() {
        return (String)getStateHelper().eval(PropertyKeys.icon, null);
    }

    public void setIcon(String icon) {
        getStateHelper().put(PropertyKeys.icon, icon);
    }

    public Boolean getActive() {
        return (Boolean)getStateHelper().eval(PropertyKeys.active, false);
    }

    public void setActive(Boolean active) {
        getStateHelper().put(PropertyKeys.active, active);
    }

    public String getJsId() {
        return (String)getStateHelper().eval(PropertyKeys.jsId, null);
    }

    public void setJsId(String jsId) {
        getStateHelper().put(PropertyKeys.jsId, jsId);
    }

}