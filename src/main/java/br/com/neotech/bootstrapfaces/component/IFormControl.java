package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.UIComponent;

public interface IFormControl {

    String getClientId();
    UIComponent getFacet(String facetName);

    String getLabel();
    String getFormLayout();
    String getInputSize();
    String getLabelSize();
    String getHint();
    String getTooltipPosition();
    String getInitScript();
    void setInitScript(String script);
    boolean isShowLabel();
    boolean isRequired();

    void preRender();
    void posRender();
}
