package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value = "ctis.bootstrapfaces.dialog")
public class Dialog extends UINamingContainer {

    enum PropertyKeys {
        title, message, jsId
    }

    public String getTitle() {
        return (String)getStateHelper().eval(PropertyKeys.title, null);
    }

    public void setTitle(String title) {
        getStateHelper().put(PropertyKeys.title, title);
    }

    public String getMessage() {
        return (String)getStateHelper().eval(PropertyKeys.message, null);
    }

    public void setMessage(String message) {
        getStateHelper().put(PropertyKeys.message, message);
    }

    public String getJsId() {
        return (String)getStateHelper().eval(PropertyKeys.jsId, null);
    }

    public void setJsId(String jsId) {
        getStateHelper().put(PropertyKeys.jsId, jsId);
    }
}