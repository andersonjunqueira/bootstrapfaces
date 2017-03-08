package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;

@FacesComponent(value = "neotech.bootstrapfaces.inputTextMask")
public class InputTextMask extends InputText {

    public static final String COMPONENT_FAMILY = "neotech.bootstrapfaces.family.inputText";

    public enum BSPropertyKeys {
        mask, placeholder
    };

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getMask() {
        return (String)getStateHelper().eval(BSPropertyKeys.mask, null);
    }

    public void setMask(String mask) {
        getStateHelper().put(BSPropertyKeys.mask, mask);
    }

    @Override
    public void posRender() {
        super.posRender();

        String mask = getMask();
        if(mask != null) {
            String script = new StringBuilder()
                .append("$('.").append(getStyleClassId()).append("').mask(")
                .append("'").append(mask).append("'")
                .append(", {});").toString();

            String initScript = getInitScript();
            if(initScript != null) {
                script = initScript + script;
            }
            setInitScript(script);
        }
    }
}