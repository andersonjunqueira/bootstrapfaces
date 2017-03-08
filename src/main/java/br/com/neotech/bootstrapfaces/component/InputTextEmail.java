package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;

import br.com.neotech.framework.faces.validator.EmailValidator;

@FacesComponent(value = "ctis.bootstrapfaces.inputTextEmail")
public class InputTextEmail extends InputText {

    public InputTextEmail() {
        addValidator(new EmailValidator());
    }

    @Override
    public String getPrepend() {
        return (String)getStateHelper().eval(BSPropertyKeys.prepend, BFConstantes.DEFAULT_EMAIL_PREPEND);
    }
}