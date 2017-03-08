package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.convert.Converter;

import br.com.neotech.framework.faces.converter.TelefoneConverter;
import br.com.neotech.framework.faces.validator.TelefoneValidator;

@FacesComponent(value = "ctis.bootstrapfaces.inputTextPhone")
public class InputTextPhone extends InputText {

    public static final String COMPONENT_FAMILY = "ctis.bootstrapfaces.family.inputText";

    public InputTextPhone() {
        addValidator(new TelefoneValidator());
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public Converter getConverter() {
        return new TelefoneConverter();
    }

    @Override
    public void posRender() {
        super.posRender();

        String script = new StringBuilder()
            .append("var " + getStyleClassId() + "_behavior = function (val) { \n")
            .append("    var vl = val.replace(/\\D/g, ''); \n")
            .append("    if(vl.indexOf('0800') == 0) { \n")
            .append("        return '0000-00-0000'; \n")
            .append("    } else {\n")
            .append("        return vl.length === 11 ? '(00) 00000-0000' : '(00) 0000-00009'; \n")
            .append("    } \n")
            .append("}, \n")
            .append(getStyleClassId() + "_options = { \n")
            .append("    onKeyPress: function(val, e, field, options) { \n")
            .append("       field.mask(" + getStyleClassId() + "_behavior.apply({}, arguments), options); \n")
            .append("    } \n")
            .append("}; \n")
            .append("$('." + getStyleClassId() + "').mask(" + getStyleClassId() + "_behavior, " + getStyleClassId() + "_options); \n").toString();

        String initScript = getInitScript();
        if(initScript != null) {
            script = initScript + script;
        }
        setInitScript(script);

    }
}