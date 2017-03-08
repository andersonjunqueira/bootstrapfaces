package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlForm;

@FacesComponent(value = "neotech.bootstrapfaces.form")
public class Form extends HtmlForm {

    public static final String COMPONENT_FAMILY = "neotech.bootstrapfaces.family.form";

    public enum BSPropertyKeys {
        formLayout, showMessages, globalOnly, showHint
    };

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getFormLayout() {
        return (String)getStateHelper().eval(BSPropertyKeys.formLayout, BFConstantes.DEFAULT_FORM_LAYOUT);
    }

    public void setFormLayout(String formLayout) {
        getStateHelper().put(BSPropertyKeys.formLayout, formLayout);
    }

    public Boolean getShowMessages() {
        return (Boolean)getStateHelper().eval(BSPropertyKeys.showMessages, BFConstantes.DEFAULT_SHOW_MESSAGES);
    }

    public void setShowMessages(Boolean showMessages) {
        getStateHelper().put(BSPropertyKeys.showMessages, showMessages);
    }

    public Boolean getGlobalOnly() {
        return (Boolean)getStateHelper().eval(BSPropertyKeys.globalOnly, BFConstantes.DEFAULT_GLOBAL_ONLY);
    }

    public void setGlobalOnly(Boolean globalOnly) {
        getStateHelper().put(BSPropertyKeys.globalOnly, globalOnly);
    }

    public Boolean getShowHint() {
        return (Boolean)getStateHelper().eval(BSPropertyKeys.showHint, BFConstantes.DEFAULT_SHOW_HINT);
    }

    public void setShowHint(Boolean showHint) {
        getStateHelper().put(BSPropertyKeys.showHint, showHint);
    }

    @Override
    public String getStyleClass() {
        String styleClass = (String)getStateHelper().eval(HtmlForm.PropertyKeys.styleClass, "");
        styleClass += " form-" + getFormLayout();
        return styleClass;
    }

}