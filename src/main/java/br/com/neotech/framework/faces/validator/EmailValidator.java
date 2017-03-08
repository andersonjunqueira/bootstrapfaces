package br.com.neotech.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.framework.faces.Mensagens;
import br.com.neotech.framework.util.ValidatorUtil;


@FacesValidator("br.com.neotech.bootstrapfaces.validator.email")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (value != null && !StringUtils.isEmpty(value.toString()) && !ValidatorUtil.validaEmail(value.toString())) {
            String msg = Mensagens.COMPONENTES.get("framework.validator.email");

            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            throw new ValidatorException(fm);
        }
    }

}
