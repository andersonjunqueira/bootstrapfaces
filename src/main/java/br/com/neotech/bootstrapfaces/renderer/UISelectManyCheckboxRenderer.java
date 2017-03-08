package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.FacesRenderer;

import org.apache.commons.lang3.StringUtils;

import br.com.neotech.bootstrapfaces.component.IFormControl;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.SelectManyCheckboxListRenderer;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;

@FacesRenderer(rendererType="neotech.bootstrapfaces.renderer.selectManyCheckbox", componentFamily="neotech.bootstrapfaces.family.selectManyCheckbox")
public class UISelectManyCheckboxRenderer extends SelectManyCheckboxListRenderer {

    private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTMANYCHECKBOX);

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);

        String alignStr;
        Object borderObj;
        boolean alignVertical = false;
        int border = 0;

        if (null != (alignStr = (String) component.getAttributes().get("layout"))) {
            alignVertical = alignStr.equalsIgnoreCase("pageDirection");
        }

        if (null != (borderObj = component.getAttributes().get("border"))) {
            border = (Integer) borderObj;
        }

        Converter converter = null;
        if(component instanceof ValueHolder) {
            converter = ((ValueHolder)component).getConverter();
        }

        renderBeginText(component, border, alignVertical, context, true);

        Iterator<SelectItem> items = RenderKitUtils.getSelectItems(context, component);

        Object currentSelections = getCurrentSelectedValues(component);
        Object[] submittedValues = getSubmittedSelectedValues(component);
        Map<String,Object> attributes = component.getAttributes();
        OptionComponentInfo optionInfo = new OptionComponentInfo(
            (String) attributes.get("disabledClass"),
            (String) attributes.get("enabledClass"),
            (String) attributes.get("unselectedClass"),
            (String) attributes.get("selectedClass"),
            Util.componentIsDisabled(component),
            isHideNoSelection(component));

        int idx = -1;
        while (items.hasNext()) {

            SelectItem curItem = items.next();
            idx++;

            if (curItem instanceof SelectItemGroup) {

                renderBeginText(component, 0, alignVertical, context, false);

                SelectItem[] itemsArray = ((SelectItemGroup) curItem).getSelectItems();

                for (int i = 0; i < itemsArray.length; ++i) {
                    renderOption(context, component, converter, itemsArray[i], currentSelections, submittedValues, alignVertical, i, optionInfo);
                }

                renderEndText(component, alignVertical, context);

            } else {

                renderOption(context, component, converter, curItem, currentSelections, submittedValues,
                    alignVertical, idx, optionInfo);
            }
        }

        renderEndText(component, alignVertical, context);

    }

    @Override
    protected void renderBeginText(UIComponent component, int border, boolean alignVertical, FacesContext context, boolean outerTable) throws IOException {

        if(outerTable) {

            if(component instanceof IFormControl) {
                RendererUtil ru = new RendererUtil();

                IFormControl fc = (IFormControl)component;
                fc.preRender();

                ru.formControlBefore(context, component);

            }
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

        String valueString = getFormattedValue(context, component, curItem.getValue(), converter);

        Object valuesArray;
        Object itemValue;

        if (submittedValues != null) {
            valuesArray = submittedValues;
            itemValue = valueString;
        } else {
            valuesArray = currentSelections;
            itemValue = curItem.getValue();
        }

        RequestStateManager.set(context,
        RequestStateManager.TARGET_COMPONENT_ATTRIBUTE_NAME,
        component);

        boolean isSelected = isSelected(context, component, itemValue, valuesArray, converter);
        if (optionInfo.isHideNoSelection()
            && curItem.isNoSelectionOption()
            && currentSelections != null
            && !isSelected) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();

        String idString = component.getClientId(context) + UINamingContainer.getSeparatorChar(context) + Integer.toString(itemNumber);

        // Set up the label's class, if appropriate
        StringBuilder labelClass = new StringBuilder();
        String style = "";

        // If disabledClass or enabledClass set, add it to the label's class
        if (optionInfo.isDisabled() || curItem.isDisabled()) {
            style = optionInfo.getDisabledClass();
        } else { // enabled
            style = optionInfo.getEnabledClass();
        }

        if (style != null) {
            labelClass.append(style);
        }

        // If selectedClass or unselectedClass set, add it to the label's class
        if (isSelected(context, component, itemValue, valuesArray, converter)) {
            style = optionInfo.getSelectedClass();
        } else { // not selected
            style = optionInfo.getUnselectedClass();
        }

        if (style != null) {
            if (labelClass.length() > 0) {
                labelClass.append(' ');
            }
            labelClass.append(style);
        }

        RendererUtil ru = new RendererUtil();

        Object[] p = new Object[2];
        p[0] = idString;
        p[1] = labelClass.toString();
        writer.write(ru.loadPartial("many-checkbox-input-begin.html", p));

        writer.startElement("input", component);
        writer.writeAttribute("name", component.getClientId(context), "clientId");
        writer.writeAttribute("id", idString, "id");
        writer.writeAttribute("value", valueString, "value");
        writer.writeAttribute("type", "checkbox", null);

        if (isSelected) {
            writer.writeAttribute(getSelectedTextString(), Boolean.TRUE, null);
        }

        if (!optionInfo.isDisabled()) {
            if (curItem.isDisabled()) {
                writer.writeAttribute("disabled", true, "disabled");
            }
        }

        RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnClickSelectBehaviors(component));
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
        RenderKitUtils.renderSelectOnclick(context, component, false);

        writer.endElement("input");

        String itemLabel = curItem.getLabel();
        if (itemLabel == null) {
            itemLabel = valueString;
        }

        p = new Object[1];
        p[0] = itemLabel;
        writer.write(ru.loadPartial("many-checkbox-input-end.html", p));

    }

    String getSelectedTextString() {
        return "checked";
    }

}
