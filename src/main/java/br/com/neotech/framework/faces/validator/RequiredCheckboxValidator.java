package br.com.neotech.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;

import br.com.neotech.framework.faces.Mensagens;

@FacesValidator("br.com.ctis.bootstrapfaces.validator.requiredCheckbox")
public class RequiredCheckboxValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value.equals(Boolean.FALSE)) {
            String msg = Mensagens.MESSAGES.get("javax.faces.component.UIInput.REQUIRED");
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            throw new ValidatorException(fm);
        }
    }

}