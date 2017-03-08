package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.el.ELException;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.render.FacesRenderer;

import org.apache.commons.lang3.StringUtils;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.RadioRenderer;
import com.sun.faces.util.RequestStateManager;

import br.com.neotech.bootstrapfaces.component.IFormControl;

@FacesRenderer(rendererType="neotech.bootstrapfaces.renderer.selectOneRadio", componentFamily="neotech.bootstrapfaces.family.selectOneRadio")
public class UISelectOneRadioRenderer extends RadioRenderer {

    private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTONERADIO);

    @Override
    protected void renderBeginText(UIComponent component, int border, boolean alignVertical, FacesContext context, boolean outerTable) throws IOException {

        if(component instanceof IFormControl) {
            IFormControl fc = (IFormControl)component;
            fc.preRender();

            RendererUtil ru = new RendererUtil();
            ru.formControlBefore(context, component);
        }

    }

    @Override
    protected void renderEndText(UIComponent component, boolean alignVertical, FacesContext context) throws IOException {

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

    @Override
    protected void renderOption(FacesContext context, UIComponent component, Converter converter, SelectItem curItem,
        Object currentSelections, Object[] submittedValues, boolean alignVertical, int itemNumber,
        OptionComponentInfo optionInfo) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        assert (writer != null);

        UISelectOne selectOne = (UISelectOne) component;
        Object curValue = selectOne.getSubmittedValue();
        if (curValue == null) {
            curValue = selectOne.getValue();
        }

        Class<?> type = String.class;
        if (curValue != null) {
            type = curValue.getClass();
            if (type.isArray()) {
                curValue = ((Object[]) curValue)[0];
                if (null != curValue) {
                    type = curValue.getClass();
                }
            } else if (Collection.class.isAssignableFrom(type)) {
                Iterator<?> valueIter = ((Collection<?>)curValue).iterator();
                if (null != valueIter && valueIter.hasNext()) {
                    curValue = valueIter.next();
                    if (null != curValue) {
                        type = curValue.getClass();
                    }
                }
            }
        }

        Object itemValue = curItem.getValue();
        RequestStateManager.set(context, RequestStateManager.TARGET_COMPONENT_ATTRIBUTE_NAME, component);
        Object newValue;

        try {
            newValue = context.getApplication().getExpressionFactory().coerceToType(itemValue, type);

        } catch (ELException ele) {
            newValue = itemValue;

        } catch (IllegalArgumentException iae) {
            // If coerceToType fails, per the docs it should throw
            // an ELException, however, GF 9.0 and 9.0u1 will throw
            // an IllegalArgumentException instead (see GF issue 1527).
            newValue = itemValue;
        }

        boolean checked = null != newValue && newValue.equals(curValue);

        if (optionInfo.isHideNoSelection()
            && curItem.isNoSelectionOption()
            && curValue != null
            && !checked) {
            return;
        }

//            <div class="radio i-checks">
//                <label>
//                    <input type="radio" name="radio[custom]" value="2" checked=""><i></i> Opção 2 checked
//                </label>
//            </div>

        String radioClass = "i-checks radio";
        if(component.getAttributes().get("layout") != null &&
            component.getAttributes().get("layout").equals("lineDirection")) {
            radioClass += " inline";
        }

        writer.startElement("div", null);
        writer.writeAttribute("class", radioClass, null);

        writer.startElement("label", component);
        writer.writeAttribute("class", "radio-label", "class");

        // RADIO CONFORME SUPERCLASS
        writer.startElement("input", component);
        writer.writeAttribute("type", "radio", "type");

        if (checked) {
            writer.writeAttribute("checked", Boolean.TRUE, null);
        }

        String idString = component.getClientId(context) + UINamingContainer.getSeparatorChar(context) + Integer.toString(itemNumber);

        writer.writeAttribute("name", component.getClientId(context), "clientId");
        writer.writeAttribute("id", idString, "id");
        writer.writeAttribute("value", (getFormattedValue(context, component, curItem.getValue(), converter)), "value");

        // Don't render the disabled attribute twice if the 'parent' component is already marked disabled.
        if (!optionInfo.isDisabled()) {
            if (curItem.isDisabled()) {
                writer.writeAttribute("disabled", true, "disabled");
            }
        }
        // Apply HTML 4.x attributes specified on UISelectMany component to all
        // items in the list except styleClass and style which are rendered as
        // attributes of outer most table.
        RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnClickSelectBehaviors(component));
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
        RenderKitUtils.renderSelectOnclick(context, component, false);

        writer.endElement("input");
        // FIM-RADIO

        writer.startElement("i", null);
        writer.endElement("i");

        String itemLabel = curItem.getLabel();
        if (itemLabel != null) {
            writer.writeText(" ", component, null);
            if (!curItem.isEscape()) {
                // It seems the ResponseWriter API should
                // have a writeText() with a boolean property
                // to determine if it content written should
                // be escaped or not.
                writer.write(itemLabel);
            } else {
                writer.writeText(itemLabel, component, "label");
            }
        }

        writer.endElement("label");

        if(curItem.getDescription() != null) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "radio-description", "class");
            writer.write(curItem.getDescription());
            writer.endElement("div");
        }

        writer.endElement("div");

    }
}