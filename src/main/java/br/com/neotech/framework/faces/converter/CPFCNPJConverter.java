package br.com.neotech.framework.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.framework.util.ConverterUtil;


@FacesConverter("br.com.neotech.bootstrapfaces.converter.cpfcnpj")
public class CPFCNPJConverter implements Converter {

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

            if(value.toString().length() == 11) {
                return ConverterUtil.aplicaMascaraCPF(value.toString());
            } else if(value.toString().length() == 14) {
                return ConverterUtil.aplicaMascaraCNPJ(value.toString());
            }

            return value.toString();

        } else {
            return null;
        }
    }

}
