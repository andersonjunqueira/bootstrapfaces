package br.com.neotech.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.fileupload.disk.DiskFileItem;

import br.com.neotech.framework.faces.Mensagens;
import lombok.Getter;
import lombok.Setter;


@FacesValidator("br.com.neotech.bootstrapfaces.validator.tamanhoArquivo")
public class TamanhoArquivoValidator implements Validator {

    @Getter @Setter
    private long tamanho = 10485760L;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null) {
            DiskFileItem i = (DiskFileItem)value;
            if(i.getSize() > tamanho) {
                String m = Mensagens.COMPONENTES.get("framework.validator.upload.tamanho");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, null);
                throw new ValidatorException(msg);
            }
        }
    }

}
