package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;

import org.apache.commons.fileupload.FileItem;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;

import br.com.neotech.framework.faces.upload.MultipartRequest;

/**
 * Faces renderer for <code>input type="file"</code> field.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2009/12/uploading-files-with-jsf-20-and-
 *       servlet.html
 */
@FacesRenderer(rendererType="ctis.bootstrapfaces.renderer.inputFile", componentFamily="ctis.bootstrapfaces.family.inputFile")
public class UIInputFileRenderer extends BasicInputRenderer {

    // Constants
    // ----------------------------------------------------------------------------------

    private static final String EMPTY_STRING = "";
    private static final Attribute[] INPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXT);

    // Actions
    // ------------------------------------------------------------------------------------

    @Override
    protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue)
        throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("input", component);
        writeIdAttributeIfNecessary(context, writer, component);
        writer.writeAttribute("type", "file", null);
        writer.writeAttribute("name", (component.getClientId(context)), "clientId");

        // Render styleClass, if any.
        String styleClass = (String) component.getAttributes().get("styleClass");
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }

        // Render standard HTMLattributes expect of styleClass.
        RenderKitUtils.renderPassThruAttributes(context, writer, component, INPUT_ATTRIBUTES, getNonOnChangeBehaviors(component));
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
        RenderKitUtils.renderOnchange(context, component, false);

        writer.endElement("input");

    }

    @Override
    public void decode(FacesContext context, UIComponent component) {

        rendererParamsNotNull(context, component);
        if (!shouldDecode(component)) {
            return;
        }

        String clientId = decodeBehaviors(context, component);
        if (clientId == null) {
            clientId = component.getClientId(context);
        }

        if(context.getExternalContext().getRequest() instanceof MultipartRequest) {
            FileItem file = ((MultipartRequest) context.getExternalContext().getRequest()).getFileItem(clientId);
            if(file != null && file.getSize() > 0) {
                ((UIInput) component).setSubmittedValue(file);
                return;
            }
        }

        ((UIInput) component).setSubmittedValue(EMPTY_STRING);

    }

    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
            throws ConverterException {
        return (submittedValue != EMPTY_STRING) ? submittedValue : null;
    }

}