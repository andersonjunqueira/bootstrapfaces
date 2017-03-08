package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value = "neotech.bootstrapfaces.pagetitle")
public class PageTitle extends UINamingContainer {

    enum PropertyKeys {
        text, message
    }

    public String getText() {
        return (String)getStateHelper().eval(PropertyKeys.text, null);
    }

    public void setText(String text) {
        getStateHelper().put(PropertyKeys.text, text);
    }

    public String getMessage() {
        return (String)getStateHelper().eval(PropertyKeys.message, null);
    }

    public void setMessage(String message) {
        getStateHelper().put(PropertyKeys.message, message);
    }

}