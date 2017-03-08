package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlInputText;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.bootstrapfaces.renderer.RendererUtil;
import br.com.neotech.framework.faces.FaceletsFunctions;

@FacesComponent(value = "ctis.bootstrapfaces.inputText")
public class InputText extends HtmlInputText implements IFormAppendable {

    public static final String COMPONENT_FAMILY = "ctis.bootstrapfaces.family.inputText";

    public enum BSPropertyKeys {
        formLayout, showLabel, inputSize, labelSize, tooltipPosition, placeholder, prepend, append, initScript, hint
    };

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public String getHint() {
        return (String)getStateHelper().eval(BSPropertyKeys.hint, null);
    }

    public void setHint(String hint) {
        getStateHelper().put(BSPropertyKeys.hint, hint);
    }

    @Override
    public String getTooltipPosition() {
        return (String)getStateHelper().eval(BSPropertyKeys.tooltipPosition, null);
    }

    public void setTooltipPosition(String tooltipPosition) {
        getStateHelper().put(BSPropertyKeys.tooltipPosition, tooltipPosition);
    }

    @Override
    public String getFormLayout() {
        return (String)getStateHelper().eval(BSPropertyKeys.formLayout, RendererUtil.findFormLayout(this));
    }

    public void setFormLayout(String formLayout) {
        getStateHelper().put(BSPropertyKeys.formLayout, formLayout);
    }

    @Override
    public boolean isShowLabel() {
        return (Boolean)getStateHelper().eval(BSPropertyKeys.showLabel, BFConstantes.DEFAULT_SHOW_LABEL);
    }

    public void setShowLabel(boolean showLabel) {
        getStateHelper().put(BSPropertyKeys.showLabel, showLabel);
    }

    @Override
    public String getInitScript() {
        return (String)getStateHelper().eval(BSPropertyKeys.initScript, null);
    }

    @Override
    public void setInitScript(String initScript) {
        getStateHelper().put(BSPropertyKeys.initScript, initScript);
    }

    @Override
    public String getLabelSize() {
        return (String)getStateHelper().eval(BSPropertyKeys.labelSize, BFConstantes.DEFAULT_LABEL_SIZE);
    }

    public void setLabelSize(String labelSize) {
        getStateHelper().put(BSPropertyKeys.labelSize, labelSize);
    }

    public String getPlaceholder() {
        return (String)getStateHelper().eval(BSPropertyKeys.placeholder, null);
    }

    public void setPlaceholder(String placeholder) {
        getStateHelper().put(BSPropertyKeys.placeholder, placeholder);
    }

    @Override
    public String getPrepend() {
        return (String)getStateHelper().eval(BSPropertyKeys.prepend, null);
    }

    public void setPrepend(String prepend) {
        getStateHelper().put(BSPropertyKeys.prepend, prepend);
    }

    @Override
    public String getAppend() {
        return (String)getStateHelper().eval(BSPropertyKeys.append, null);
    }

    public void setAppend(String append) {
        getStateHelper().put(BSPropertyKeys.append, append);
    }

    @Override
    public String getInputSize() {
        return (String)getStateHelper().eval(BSPropertyKeys.inputSize, BFConstantes.DEFAULT_INPUT_SIZE);
    }

    public void setInputSize(String inputSize) {
        getStateHelper().put(BSPropertyKeys.inputSize, inputSize);
    }

    @Override
    public String getStyleClass() {

        String styleClass = super.getStyleClass();
        if(StringUtils.isEmpty(styleClass)) {
            styleClass = "";
        }

        if(!styleClass.contains(BFConstantes.DEFAULT_STYLE_CLASS)) {
            styleClass += BFConstantes.DEFAULT_STYLE_CLASS;
        }

        if(!styleClass.contains(getStyleClassId())) {
            styleClass += getStyleClassId();
        }

        return styleClass;
    }

    @Override
    public String getDir() {
        return (String)getStateHelper().eval(HtmlInputText.PropertyKeys.dir, BFConstantes.DEFAULT_DIR);
    }

    public String getStyleClassId() {
        // O JSF usa o caracter ":" para identificar o componente
        // O jQuery usa o caracter ":" como reservado
        // Para evitar o conflito, eu substituo os ":" do id do componente por "_" e adiciono como classe CSS
        // Assim o jQuery o código referenciar os componentes JSF individualmente.
        return FaceletsFunctions.styleClassId(this);
    }

    @Override
    public void preRender() {
    }

    @Override
    public void posRender() {

        if(getTooltipPosition() != null) {
            String script = new StringBuilder()
                .append("$('.").append(getStyleClassId())
                .append("').tooltip();").toString();

            String initScript = getInitScript();
            if(initScript != null) {
                script = initScript + script;
            }
            setInitScript(script);
        }

    }
}