package br.com.neotech.bootstrapfaces.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;

@FacesRenderer(rendererType="ctis.bootstrapfaces.renderer.inputTextarea", componentFamily="ctis.bootstrapfaces.family.inputTextarea")
public class UIInputTextAreaRenderer extends BasicInputRenderer {

    private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXTAREA);

    @Override
    protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws java.io.IOException {

        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);

        String styleClass =
              (String) component.getAttributes().get("styleClass");

        writer.startElement("textarea", component);
        writeIdAttributeIfNecessary(context, writer, component);
        writer.writeAttribute("name", component.getClientId(context), "clientId");

        if (null != styleClass) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }

        writeExtraAttributes(context, component, currentValue);

        // style is rendered as a passthru attribute
        RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);

        RenderKitUtils.renderOnchange(context, component, false);

        if (component.getAttributes().containsKey("com.sun.faces.addNewLineAtStart") &&
            "true".equalsIgnoreCase((String) component.getAttributes().get("com.sun.faces.addNewLineAtStart"))) {
            writer.writeText("\n", null);
        }

        // render default text specified
        if (currentValue != null) {
            writer.writeText(currentValue, component, "value");
        }

        writer.endElement("textarea");

    }

}