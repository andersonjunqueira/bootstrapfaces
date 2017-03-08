package br.com.neotech.framework.faces.converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.bootstrapfaces.component.InputTextNumber;
import br.com.neotech.framework.faces.Mensagens;


@FacesConverter("br.com.neotech.bootstrapfaces.converter.decimal")
public class DecimalConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if(StringUtils.isEmpty(value)) {
            return null;

        } else {
            try {

                Locale loc = FacesContext.getCurrentInstance().getViewRoot().getLocale();
                DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(loc);

                if(component instanceof InputTextNumber) {

                    InputTextNumber c = (InputTextNumber)component;
                    String sepMilhar = c.getThousandsSeparator();
                    String sepDecimal = c.getDecimalSeparator();
                    int casasDecimais = c.getMinFractionDigits();

                    df.setMinimumFractionDigits(casasDecimais);
                    if(sepDecimal != null) {
                        df.getDecimalFormatSymbols().setDecimalSeparator(sepDecimal.charAt(0));
                        df.getDecimalFormatSymbols().setGroupingSeparator(sepMilhar.charAt(0));
                    }
                }

                return df.parse(value);

            } catch(ParseException ex) {
                String msg = Mensagens.COMPONENTES.get("framework.converter.decimal");
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
                throw new ConverterException(fm);
            }
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null && !value.toString().equals("")) {

            Locale loc = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(loc);

            if(component instanceof InputTextNumber) {

                InputTextNumber c = (InputTextNumber)component;
                String sepMilhar = c.getThousandsSeparator();
                String sepDecimal = c.getDecimalSeparator();
                int casasDecimais = c.getMinFractionDigits();

                df.setMinimumFractionDigits(casasDecimais);
                if(sepDecimal != null) {
                    df.getDecimalFormatSymbols().setDecimalSeparator(sepDecimal.charAt(0));
                    df.getDecimalFormatSymbols().setGroupingSeparator(sepMilhar.charAt(0));
                }
            }

            return df.format(value);

        } else {
            return "";
        }
    }

}
