package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.bootstrapfaces.renderer.RendererUtil;
import br.com.neotech.framework.faces.FaceletsFunctions;

@FacesComponent(value = "neotech.bootstrapfaces.formControl")
public class FormControl extends UIComponentBase implements IFormControl {

    public static final String COMPONENT_FAMILY = "neotech.bootstrapfaces.family.formControl";

    public enum BSPropertyKeys {
        forAttribute, formLayout, showLabel, inputSize, labelSize, styleClass, label, hint, tooltipPosition, initScript,
        required
    };

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getFor() {
        return (String)getStateHelper().eval(BSPropertyKeys.forAttribute, null);
    }

    public void setFor(String forAttribute) {
        getStateHelper().put(BSPropertyKeys.forAttribute, forAttribute);
    }

    @Override
    public String getFormLayout() {
        return (String)getStateHelper().eval(BSPropertyKeys.formLayout, RendererUtil.findFormLayout(this));
    }

    public void setFormLayout(String formLayout) {
        getStateHelper().put(BSPropertyKeys.formLayout, formLayout);
    }

    @Override
    public String getInputSize() {
        return (String)getStateHelper().eval(BSPropertyKeys.inputSize, BFConstantes.DEFAULT_INPUT_SIZE);
    }

    public void setInputSize(String inputSize) {
        getStateHelper().put(BSPropertyKeys.inputSize, inputSize);
    }

    @Override
    public String getLabelSize() {
        return (String)getStateHelper().eval(BSPropertyKeys.labelSize, BFConstantes.DEFAULT_LABEL_SIZE);
    }

    public void setLabelSize(String labelSize) {
        getStateHelper().put(BSPropertyKeys.labelSize, labelSize);
    }

    public void setStyleClass(String styleClass) {
        getStateHelper().put(BSPropertyKeys.styleClass, styleClass);
    }

    public String getStyleClass() {

        String styleClass = (String)getStateHelper().put(BSPropertyKeys.styleClass, "");
        if(StringUtils.isEmpty(styleClass)) {
            styleClass = "";
        }

        if(!styleClass.contains(getStyleClassId())) {
            styleClass += getStyleClassId();
        }

        return styleClass;
    }

    @Override
    public String getLabel() {
        return (String)getStateHelper().eval(BSPropertyKeys.label, null);
    }

    public void setLabel(String label) {
        getStateHelper().put(BSPropertyKeys.label, label);
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
        return null;
    }

    public void setTooltipPosition(String tooltipPosition) {
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
    public boolean isShowLabel() {
        return (Boolean)getStateHelper().eval(BSPropertyKeys.showLabel, BFConstantes.DEFAULT_SHOW_LABEL);
    }

    public void setShowLabel(Boolean showLabel) {
        getStateHelper().put(BSPropertyKeys.showLabel, showLabel);
    }

    @Override
    public boolean isRequired() {
        return (Boolean)getStateHelper().eval(BSPropertyKeys.required, false);
    }

    public void setRequired(boolean required) {
        getStateHelper().put(BSPropertyKeys.required, required);
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
    }

}