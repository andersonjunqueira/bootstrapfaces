package br.com.neotech.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.framework.faces.Mensagens;
import br.com.neotech.framework.util.ValidatorUtil;


@FacesValidator("br.com.neotech.bootstrapfaces.validator.cnpj")
public class CNPJValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (value != null && !StringUtils.isEmpty(value.toString()) && !ValidatorUtil.validaCNPJ(value.toString())) {
            String m = Mensagens.COMPONENTES.get("framework.validator.cnpj");

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, null);
            throw new ValidatorException(msg);
        }

    }

}
