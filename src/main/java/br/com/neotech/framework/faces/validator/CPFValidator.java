package br.com.neotech.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.framework.faces.Mensagens;
import br.com.neotech.framework.util.ValidatorUtil;


@FacesValidator("br.com.ctis.bootstrapfaces.validator.cpf")
public class CPFValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (value != null && !StringUtils.isEmpty(value.toString()) && !ValidatorUtil.validaCPF(value.toString())) {
            String m = Mensagens.COMPONENTES.get("framework.validator.cpf");

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, null);
            throw new ValidatorException(msg);
        }

    }

}
