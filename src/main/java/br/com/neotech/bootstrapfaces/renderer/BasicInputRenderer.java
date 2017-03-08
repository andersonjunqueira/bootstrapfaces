package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.bootstrapfaces.component.IFormControl;

import com.sun.faces.renderkit.html_basic.HtmlBasicInputRenderer;

public abstract class BasicInputRenderer extends HtmlBasicInputRenderer {

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);

        if(component instanceof IFormControl) {
            IFormControl fc = (IFormControl)component;
            fc.preRender();

            RendererUtil ru = new RendererUtil();
            ru.formControlBefore(context, component);
        }

    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);

        if(component instanceof IFormControl) {

            RendererUtil ru = new RendererUtil();
            ru.formControlAfter(context, component);

            IFormControl fc = (IFormControl)component;
            fc.posRender();

            ResponseWriter writer = context.getResponseWriter();
            if(!StringUtils.isEmpty(fc.getInitScript())) {
                writer.startElement("script", null);
                writer.writeAttribute("type", "text/javascript", null);
                writer.write("\n" + fc.getInitScript() + "\n");
                writer.endElement("script");
            }
        }

    }

    protected void writeExtraAttributes(FacesContext context, UIComponent component, String currentValue) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        if(component instanceof IFormControl) {
            IFormControl fc = (IFormControl)component;

            if(fc.getTooltipPosition() != null) {
                writer.writeAttribute("data-toggle", "tooltip", null);
                writer.writeAttribute("data-placement", fc.getTooltipPosition(), null);
            }
        }
    }

}