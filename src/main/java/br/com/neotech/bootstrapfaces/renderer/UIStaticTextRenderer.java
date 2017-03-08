package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

@FacesRenderer(rendererType="neotech.bootstrapfaces.renderer.staticText", componentFamily="neotech.bootstrapfaces.family.staticText")
public class UIStaticTextRenderer extends BasicInputRenderer {

    @Override
    protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        RendererUtil ru = new RendererUtil();

        Object[] p = new Object[1];
        p[0] = currentValue;
        writer.write(ru.loadPartial("formstatic.html", p));

    }

}