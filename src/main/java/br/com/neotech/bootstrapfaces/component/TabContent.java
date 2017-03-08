package br.com.neotech.bootstrapfaces.component;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("neotech.bootstrapfaces.tabContent")
public class TabContent extends UINamingContainer {

    enum PropertyKeys {
        active, jsId
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