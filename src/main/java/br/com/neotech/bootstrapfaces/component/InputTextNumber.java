package br.com.neotech.bootstrapfaces.component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import br.com.neotech.framework.faces.converter.DecimalConverter;

@FacesComponent(value = "neotech.bootstrapfaces.inputTextNumber")
public class InputTextNumber extends InputText {

    public enum BSPropertyKeys {
        thousandsSeparator, decimalSeparator, minFractionDigits
    };

    public String getThousandsSeparator() {
        return (String)getStateHelper().eval(BSPropertyKeys.thousandsSeparator, null);
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        getStateHelper().put(BSPropertyKeys.thousandsSeparator, thousandsSeparator);
    }

    public String getDecimalSeparator() {
        return (String)getStateHelper().eval(BSPropertyKeys.decimalSeparator, null);
    }

    public void setDecimalSeparator(String decimalSeparator) {
        getStateHelper().put(BSPropertyKeys.decimalSeparator, decimalSeparator);
    }

    public Integer getMinFractionDigits() {
        return (Integer)getStateHelper().eval(BSPropertyKeys.minFractionDigits, BFConstantes.DEFAULT_MIN_FRACTION_DIGITS);
    }

    public void setMinFractionDigits(Integer minFractionDigits) {
        getStateHelper().put(BSPropertyKeys.minFractionDigits, minFractionDigits);
    }

    @Override
    public String getDir() {
        return (String)getStateHelper().eval(HtmlInputText.PropertyKeys.dir, "rtl");
    }

    @Override
    public void preRender() {
        super.preRender();

        FacesContext fctx = FacesContext.getCurrentInstance();
        Locale loc = fctx.getViewRoot().getLocale();

        String sepMilhar = getThousandsSeparator();
        String sepDecimal = getDecimalSeparator();
        Integer casasDecimais = getMinFractionDigits();

        if(sepDecimal == null) {
            DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(loc);
            sepMilhar = Character.toString(df.getDecimalFormatSymbols().getGroupingSeparator());
            sepDecimal = Character.toString(df.getDecimalFormatSymbols().getDecimalSeparator());
        }

        String script = new StringBuilder()
            .append("$('.").append(getStyleClassId()).append("').autoNumeric('init', {")
            .append("aSep: '").append(sepMilhar).append("', ")
            .append("aDec: '").append(sepDecimal).append("', ")
            .append("mDec: '").append(casasDecimais).append("' ")
            .append("});").toString();

        String initScript = getInitScript();
        if(initScript != null) {
            script = initScript + script;
        }
        setInitScript(script);

        DecimalConverter cv = new DecimalConverter();
        setConverter(cv);

    }

    private void definePlaceholder(Integer casasDecimais, String sepDecimal) {
        String zeros = "000000000000000000000000000000000";
        String placeholder = "0";
        if(casasDecimais > 0) {
            placeholder += sepDecimal + zeros.substring(0, casasDecimais);
        }
        setPlaceholder(placeholder);
    }
}