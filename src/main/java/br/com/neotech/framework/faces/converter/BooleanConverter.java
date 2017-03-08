package br.com.neotech.framework.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;


@FacesConverter("br.com.neotech.bootstrapfaces.converter.boolean")
public class BooleanConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if(StringUtils.isEmpty(value)) {
            return Boolean.FALSE;
        }

        if(Integer.valueOf(value) == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            return value.toString();
        } else {
            return "0";
        }
    }

}
