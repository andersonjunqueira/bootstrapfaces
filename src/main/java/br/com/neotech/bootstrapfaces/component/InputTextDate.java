package br.com.neotech.bootstrapfaces.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

@FacesComponent(value = "ctis.bootstrapfaces.inputTextDate")
public class InputTextDate extends InputText {

    public enum BSPropertyKeys {
        startView, format
    };

    public String getStartView() {
        return (String)getStateHelper().eval(BSPropertyKeys.startView, BFConstantes.DEFAULT_DATE_VIEW);
    }

    public void setStartView(String startView) {
        getStateHelper().put(BSPropertyKeys.startView, startView);
    }

    public String getFormat() {
        return (String)getStateHelper().eval(BSPropertyKeys.format, null);
    }

    public void setFormat(String format) {
        getStateHelper().put(BSPropertyKeys.format, format);
    }

    @Override
    public String getAppend() {
        return "<i class=\"fa fa-calendar\"></i>";
    }

    @Override
    public String getPlaceholder() {
        String pattern = getDatePattern().toLowerCase();
        return  pattern.replaceAll("[dym]", "_");
    }

    @Override
    public Converter getConverter() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        Locale loc = fctx.getViewRoot().getLocale();
        String pattern = getDatePattern();

        DateTimeConverter dtc = new DateTimeConverter();
        dtc.setLocale(loc);
        dtc.setTimeZone(TimeZone.getDefault());
        dtc.setPattern(pattern);

        return dtc;
    }

    @Override
    public void posRender() {
        super.posRender();

        FacesContext fctx = FacesContext.getCurrentInstance();
        Locale loc = fctx.getViewRoot().getLocale();
        String lang = loc.toString().replaceAll("_", "-");

        String script = new StringBuilder()
            .append("$('.").append(getStyleClassId()).append("').datetimepicker({")
            .append("format: '").append(getDatePattern().toUpperCase()).append("', ")
            .append("locale: '").append(lang).append("', ")
            .append("viewMode: '").append(getStartView()).append("', ")
            .append("});").toString();

        String initScript = getInitScript();
        if(initScript != null) {
            script = initScript + script;
        }
        setInitScript(script);

    }

    private String getDatePattern() {

        String pattern = "";

        if(getFormat() == null) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            Locale locale = fctx.getViewRoot().getLocale();
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);

            if (df instanceof SimpleDateFormat) {
                SimpleDateFormat sdf = (SimpleDateFormat) df;
                pattern = sdf.toPattern().replaceAll("y+","yyyy");
                sdf.applyPattern(pattern);
            }

            return pattern;

        } else {
            pattern = getFormat();
            pattern = pattern.replaceAll("m", "M");
            pattern = pattern.replaceAll("Y", "y");
        }

        return pattern;
    }
}