package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlOutputText;

import br.com.neotech.bootstrapfaces.renderer.RendererUtil;

@FacesComponent(value = "ctis.bootstrapfaces.staticText")
public class StaticText extends HtmlOutputText implements IFormControl {

    public static final String COMPONENT_FAMILY = "ctis.bootstrapfaces.family.staticText";

    public enum BSPropertyKeys {
        label, formLayout, inputSize, labelSize, hint, tooltipPosition, initScript, showLabel
    };

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public String getLabel() {
        return (String)getStateHelper().eval(BSPropertyKeys.label, null);
    }

    public void setLabel(String label) {
        getStateHelper().put(BSPropertyKeys.label, label);
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
        return false;
    }

    public void setRequired(Boolean required) {
    }

    @Override
    public void preRender() {
    }

    @Override
    public void posRender() {
    }

}