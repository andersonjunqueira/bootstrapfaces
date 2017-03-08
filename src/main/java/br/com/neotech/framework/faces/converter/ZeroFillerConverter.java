package br.com.neotech.framework.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.framework.util.ConverterUtil;


@FacesConverter("br.com.neotech.bootstrapfaces.converter.zeroFiller")
public class ZeroFillerConverter implements Converter {

    @Getter @Setter
    private Integer minIntegerDigits = 0;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(StringUtils.isEmpty(value)) {
            return null;
        } else {
            return ConverterUtil.limpaMascara(value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            String saida = value.toString();
            while(saida.length() < minIntegerDigits) {
                saida = "0" + saida;
            }
            return saida;
        } else {
            return "";
        }
    }

}
