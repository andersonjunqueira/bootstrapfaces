package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.bootstrapfaces.component.BFConstantes;
import br.com.neotech.bootstrapfaces.component.IFormControl;

import com.sun.faces.renderkit.html_basic.CheckboxRenderer;

@FacesRenderer(rendererType="neotech.bootstrapfaces.renderer.selectBooleanCheckbox", componentFamily="neotech.bootstrapfaces.family.selectBooleanCheckbox")
public class UISelectBooleanCheckboxRenderer extends CheckboxRenderer {

    @Override
    protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        RendererUtil ru = new RendererUtil();

        if(component instanceof IFormControl) {
            IFormControl fc = (IFormControl)component;

            fc.preRender();

            if(fc.getFormLayout().equals(BFConstantes.LAYOUT_INLINE)) {
                writer.write(ru.loadPartial("checkbox-inline-begin.html", (Object[])null));

            } else if(fc.getFormLayout().equals(BFConstantes.LAYOUT_VERTICAL)) {
                writer.write(ru.loadPartial("checkbox-vertical-begin.html", (Object[])null));

            } else if(fc.getFormLayout().equals(BFConstantes.LAYOUT_HORIZONTAL)) {
                Object[] p = ru.getFormGroupParams(fc);
                writer.write(ru.loadPartial("formgroup-begin.html", p));

                p = ru.getInputGridParams(fc);
                writer.write(ru.loadPartial("formgroup-grid-begin.html", p));

                writer.write(ru.loadPartial("checkbox-vertical-begin.html", (Object[])null));
            }

            super.getEndTextToRender(context, component, currentValue);

            if(fc.getFormLayout().equals(BFConstantes.LAYOUT_INLINE)) {
                Object[] p = new Object[1];
                p[0] = fc.getLabel();
                writer.write(ru.loadPartial("checkbox-inline-end.html", p));

            } else if(fc.getFormLayout().equals(BFConstantes.LAYOUT_VERTICAL)) {
                Object[] p = new Object[1];
                p[0] = fc.getLabel();
                writer.write(ru.loadPartial("checkbox-vertical-end.html", p));

            } else if(fc.getFormLayout().equals(BFConstantes.LAYOUT_HORIZONTAL)) {
                Object[] p = new Object[1];
                p[0] = fc.getLabel();
                writer.write(ru.loadPartial("checkbox-vertical-end.html", p));

                writer.write(ru.loadPartial("formgroup-grid-end.html", (Object[])null));

                writer.write(ru.loadPartial("formgroup-end.html", (Object[])null));
            }

            fc.posRender();

            if(!StringUtils.isEmpty(fc.getInitScript())) {
                writer.startElement("script", null);
                writer.writeAttribute("type", "text/javascript", null);
                writer.write("\n" + fc.getInitScript() + "\n");
                writer.endElement("script");
            }
        }
    }

}