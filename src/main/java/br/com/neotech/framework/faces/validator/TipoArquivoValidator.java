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


@FacesValidator("br.com.neotech.bootstrapfaces.validator.tipoArquivo")
public class TipoArquivoValidator implements Validator {

    @Getter @Setter
    private String tipos = null;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null && tipos != null) {

            DiskFileItem i = (DiskFileItem)value;
            String ext = i.getName();

            boolean possuiExtensao = ext.lastIndexOf(".") > -1;
            if(possuiExtensao) {
                ext = ext.substring(ext.lastIndexOf(".")+1).toLowerCase();
                String tiposPermitidos = tipos.toLowerCase();

                if(!tiposPermitidos.contains(ext)) {
                    String m = Mensagens.COMPONENTES.get("framework.validator.upload.tipoinvalido");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, null);
                    throw new ValidatorException(msg);
                }

            } else {
                String m = Mensagens.COMPONENTES.get("framework.validator.upload.tipoindefinido");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, null);
                throw new ValidatorException(msg);
            }
        }
    }
}

