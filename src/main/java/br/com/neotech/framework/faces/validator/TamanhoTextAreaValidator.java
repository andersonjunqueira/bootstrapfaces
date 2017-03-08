package br.com.neotech.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.neotech.framework.faces.Mensagens;
import lombok.Getter;
import lombok.Setter;


@FacesValidator("br.com.neotech.bootstrapfaces.validator.tamanhoTextArea")
public class TamanhoTextAreaValidator implements Validator {

    @Getter @Setter
    private long maxlength = 500;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null) {
            String i = (String)value;
            if(i.length() > maxlength) {
                String m = Mensagens.COMPONENTES.get("framework.validator.upload.tamanhotextarea", new Object[] {maxlength});
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, null);
                throw new ValidatorException(msg);
            }
        }
    }

}
