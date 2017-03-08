package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.sun.faces.renderkit.html_basic.FormRenderer;

import br.com.neotech.bootstrapfaces.component.Form;

@FacesRenderer(rendererType="ctis.bootstrapfaces.renderer.form", componentFamily="ctis.bootstrapfaces.family.form")
public class UIFormRenderer extends FormRenderer {

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        RendererUtil ru = new RendererUtil();
        ru.renderFormHint(context, component);
        addMessagesComponent(context, (Form)component);
        super.encodeChildren(context, component);
    }

    private void addMessagesComponent(FacesContext context, Form f) {

        boolean encontrado = false;
        for(UIComponent c : f.getChildren()) {
            if(c.getId().equals("messages")) {
                encontrado = true;
                break;
            }
        }

        if(!encontrado) {
            Application app = context.getApplication();
            HtmlMessages m = (HtmlMessages)app.createComponent(HtmlMessages.COMPONENT_TYPE);
            m.setId("messages");
            m.setGlobalOnly(f.getGlobalOnly());
            m.setErrorClass("alert alert-danger");
            m.setInfoClass("alert alert-info");
            m.setWarnClass("alert alert-warning");
            m.setFatalClass("alert alert-fatal");
            m.setStyleClass("global-messages list-unstyled");
            m.setRendered(f.getShowMessages());
            f.getChildren().add(0, m);
        }
    }

}