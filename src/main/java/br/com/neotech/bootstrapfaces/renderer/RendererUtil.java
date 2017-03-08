package br.com.neotech.bootstrapfaces.renderer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIMessage;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import br.com.neotech.bootstrapfaces.component.BFConstantes;
import br.com.neotech.bootstrapfaces.component.Form;
import br.com.neotech.bootstrapfaces.component.FormControl;
import br.com.neotech.bootstrapfaces.component.IFormAppendable;
import br.com.neotech.bootstrapfaces.component.IFormControl;
import br.com.neotech.bootstrapfaces.component.SelectBooleanCheckbox;
import br.com.neotech.framework.faces.Mensagens;
import lombok.Cleanup;


public class RendererUtil {

    // Campos -------------------------------------------------------------------------------------

    private boolean decorationChecked;
    private boolean prepend;
    private boolean facetPrepend;
    private boolean facetPrependBtn;
    private boolean append;
    private boolean facetAppend;
    private boolean facetAppendBtn;

    private Map<String, String> partialCache;

    private boolean haveMessage;
    private FacesMessage.Severity messageSeverity;

    protected void formControlBefore(FacesContext context, UIComponent component) throws IOException {

        ResponseWriter writer = context.getResponseWriter();

        // Rascunho -----------------------------------------------------------------------------------
        // 1.      <div id="id_componente" class="form-group">  // FORM GROUP
        // 2.          <label for="input" class="[required] [sr-only] [col-sm-2 control-label]">E-mail</label> // LABEL
        // 3.          [<div class="col-sm-10">] // INPUT GRID


        // 4.              [<div class="input-group">]  // INPUT GROUP
        // 5.                  [<span class="input-group-addon">???</span>]  // PREPEND
        // 6.                  [<span class="input-group-btn">???</span>]

        //                        // RENDER INPUT

        // 7.                  [<span class="input-group-addon">???</span>]  // APPEND
        // 8.                  [<span class="input-group-btn">???</span>]
        // 9.                  [<span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>]  // FEEDBACK ICON
        // 10.             [</div>]  // INPUT GROUP


        // 11.             [<span class="help-block m-b-none">]  // HELP BLOCK
        // 12.                 [<small>]
        // 13.                     [<h:message for="#{messageFor}"/>|hint text]
        // 14.                 [</small>]
        // 15.             [</span>]
        // 16.         [</div>] // INPUT GRID
        // 17.     </div>  // FORM GROUP
        // --------------------------------------------------------------------------------------------

        checkMessages(component);
        IFormControl fc = (IFormControl)component;

        // 1.      <div id="id_componente" class="form-group">  // FORM GROUP
        Object[] p = getFormGroupParams(fc);
        writer.write(loadPartial("formgroup-begin.html", p));

        // 2.          <label for="input" class="[required] [sr-only] [col-sm-2 control-label]">E-mail</label> // LABEL
        p = getLabelParams(fc);
        writer.write(loadPartial("formgroup-label.html", p));

        // 3.          [<div class="col-sm-10">] // INPUT GRID
        if(fc.getFormLayout().equals(BFConstantes.LAYOUT_HORIZONTAL)) {
            p = getInputGridParams(fc);
            writer.write(loadPartial("formgroup-grid-begin.html", p));
        }

        if(showInputGroup(fc)) {

            // 4.              [<div class="input-group">]  // INPUT GROUP
            writer.write(loadPartial("formgroup-inputgroup-begin.html", (Object[])null));

            if(prepend || facetPrepend) {

                // 5.                  [<span class="input-group-addon">???</span>]
                writer.write(loadPartial("formgroup-addon-begin.html", (Object[])null));

                if(prepend) {
                    writer.write(((IFormAppendable)component).getPrepend());
                } else if(facetPrepend) {
                    component.getFacet("prepend").encodeAll(context);
                }

                writer.write(loadPartial("formgroup-addon-end.html", (Object[])null));

            } else if(facetPrependBtn){

                // 6.                  [<span class="input-group-btn">???</span>]
                writer.write(loadPartial("formgroup-addonbtn-begin.html", (Object[])null));
                component.getFacet("prependBtn").encodeAll(context);
                writer.write(loadPartial("formgroup-addonbtn-end.html", (Object[])null));

            }
        }
    }

    protected void formControlAfter(FacesContext context, UIComponent component) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        Object[] p = null;

        checkMessages(component);
        IFormControl fc = (IFormControl)component;

        if(showInputGroup(fc)) {

            if(append || facetAppend) {

                // 7.                  [<span class="input-group-addon">???</span>]  // APPEND
                writer.write(loadPartial("formgroup-addon-begin.html", (Object[])null));

                if(append) {
                    writer.write(((IFormAppendable)component).getAppend());
                } else if(facetAppend) {
                    component.getFacet("append").encodeAll(context);
                }

                writer.write(loadPartial("formgroup-addon-end.html", (Object[])null));

            } else if(facetAppendBtn){

                // 8.                  [<span class="input-group-btn">???</span>]
                writer.write(loadPartial("formgroup-addonbtn-begin.html", (Object[])null));
                component.getFacet("appendBtn").encodeAll(context);
                writer.write(loadPartial("formgroup-addonbtn-end.html", (Object[])null));

            }

            // NOT IMPLEMENTED
            // 9.                  [<span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>]  // FEEDBACK ICON

            // 10.             [</div>]  // INPUT GROUP
            writer.write(loadPartial("formgroup-inputgroup-end.html", (Object[])null));
        }

        if(fc.getHint() != null || haveMessage ) {

            // 11.             [<span class="help-block m-b-none">]  // HELP BLOCK
            // 12.                 [<small>]
            p = getHelpBlockParams(fc);
            if(haveMessage) {
                p[0] = "";
            }
            writer.write(loadPartial("formgroup-helpblock-begin.html", p));

            // 13.                     [<h:message for="#{messageFor}"/>|hint text]
            if(haveMessage) {
                UIMessage m = new UIMessage();
                m.setFor(component.getClientId());
                m.encodeAll(context);
            }

            // 14.                 [</small>]
            // 15.             [</span>]
            writer.write(loadPartial("formgroup-helpblock-end.html", (Object[])null));
        }

        // 16.         [</div>] // INPUT GRID
        if(fc.getFormLayout().equals(BFConstantes.LAYOUT_HORIZONTAL)) {
            writer.write(loadPartial("formgroup-grid-end.html", (Object[])null));
        }

        // 17.     </div>  // FORM GROUP
        writer.write(loadPartial("formgroup-end.html", (Object[])null));

    }

    public Object[] getFormGroupParams(IFormControl component) {

        String groupClass = "";
        if(haveMessage) {
            String sev = "error";
            if(messageSeverity == FacesMessage.SEVERITY_WARN) {
                sev = "warning";
            } else if(messageSeverity == FacesMessage.SEVERITY_INFO) {
                sev = "success";
            }
            groupClass += "has-" + sev;
        }

        if(component instanceof FormControl) {
            groupClass += " " + ((FormControl) component).getStyleClass() + " ";
        }

        Object[] params = new Object[2];
        params[0] = component.getClientId() + "-componente";
        params[1] = groupClass;
        return params;
    }

    public Object[] getInputGridParams(IFormControl component) {

        String gridStyles = getBSColumnStyles(component.getInputSize());
        if(component instanceof SelectBooleanCheckbox) {
            gridStyles += getBSCheckboxOffsetStyles(component.getLabelSize());
        }

        Object[] params = new Object[2];
        params[0] = gridStyles;
        params[1] = "";
        return params;
    }

    public Object[] getLabelParams(IFormControl component) {

        String labelClass = !component.getFormLayout().equals(BFConstantes.LAYOUT_INLINE) ? BFConstantes.DEFAULT_LABEL_CLASS : "";

        if(component.getFormLayout().equals(BFConstantes.LAYOUT_HORIZONTAL)) {
            labelClass += getBSColumnStyles(component.getLabelSize());
            if(!component.isShowLabel()) {
                labelClass += " sr-only hidden";
            }

        } else if(component.getFormLayout().equals(BFConstantes.LAYOUT_INLINE)) {
            labelClass += " sr-only";
        }

        if(component.isRequired()) {
            if(!component.getFormLayout().equals(BFConstantes.LAYOUT_INLINE)) {
                if(component.isShowLabel()) {
                    labelClass += " required";
                }
            }
        }

        Object[] params = new Object[4];
        if(component instanceof FormControl) {
            params[0] = ((FormControl)component).getFor();
        } else {
            params[0] = component.getClientId();
        }

        params[1] = labelClass;
        params[2] = component.getLabel();
        params[3] = " ";
        return params;
    }

    public Object[] getHelpBlockParams(IFormControl component) {
        Object[] params = new Object[1];
        params[0] = component.getHint();
        return params;
    }

    // AUX

    public boolean showInputGroup(IFormControl component) {

        if(component instanceof IFormAppendable) {
            IFormAppendable fa = (IFormAppendable)component;
            if(!decorationChecked) {
                prepend = fa.getPrepend() != null;
                facetPrepend = fa.getFacet("prepend") != null;
                facetPrependBtn = fa.getFacet("prependBtn") != null;
                append = fa.getAppend() != null;
                facetAppend = fa.getFacet("append") != null;
                facetAppendBtn = fa.getFacet("appendBtn") != null;
                decorationChecked = true;
            } else {
                prepend = false;
                facetPrepend = false;
                facetPrependBtn = false;
                append = false;
                facetAppend = false;
                facetAppendBtn = false;
                decorationChecked = true;
            }
        }

        return prepend || facetPrepend || facetPrependBtn || append || facetAppend || facetAppendBtn ||
               (component.getFormLayout().equals(BFConstantes.LAYOUT_VERTICAL) && component instanceof HtmlSelectOneRadio);
    }

    public void renderFormHint(FacesContext context, UIComponent component) throws IOException {

//      <p class="form-hint">
//          <i>
//              <h:outputText value="#{comps['framework.form.camposobrigatorios']}" rendered="#{empty cc.attrs.hint}"/>
//              <h:outputText value="#{cc.attrs.hint}" rendered="#{!empty cc.attrs.hint}"/>
//          </i>
//      </p>

      if(((Form)component).getShowHint() && isChildrenRequired(component)) {
          ResponseWriter writer = context.getResponseWriter();
          writer.startElement("p", null);
          writer.writeAttribute("class", "form-hint", null);
          writer.startElement("i", null);
          writer.write(Mensagens.COMPONENTES.get("framework.form.camposobrigatorios"));
          writer.endElement("i");
          writer.endElement("p");
      }

    }

    private boolean isChildrenRequired(UIComponent component) {

        for(UIComponent c : component.getChildren()) {
            if(c instanceof UIInput) {
                if(((UIInput)c).isRequired()) {
                    return true;
                }
            } else if(c.getChildCount() > 0) {
                boolean creq = isChildrenRequired(c);
                if(creq) {
                    return true;
                }
            }
        }

        return false;
    }

    // Mensagens do componente --------------------------------------------------------------------

    public void checkMessages(UIComponent component) {
        FacesContext context = FacesContext.getCurrentInstance();

        haveMessage = context.getMessageList(component.getClientId()).size() > 0;
        messageSeverity = null;

        if(haveMessage) {
            List<FacesMessage> messages = context.getMessageList(component.getClientId());
            for(FacesMessage m : messages) {
                messageSeverity = m.getSeverity();
                break;
            }
        }
    }

    // Manipulacao template -----------------------------------------------------------------------

    public String loadPartial(String filename, Object... args) throws IOException {

        if(partialCache == null) {
            partialCache = new HashMap<String, String>();
        }

        String partial = partialCache.get(filename);
        if(partial == null) {
            partial = loadFile(filename);
            partialCache.put(filename, partial);
        }

        if(args != null && args.length > 0) {
            partial = parsePartial(partial, args);
        }

        return partial;
    }

    private String loadFile(String filename) throws IOException {

        @Cleanup
        InputStream pis = this.getClass().getClassLoader().getResourceAsStream("META-INF/resources/layouts/partials/" + filename);
        StringBuffer saida = new StringBuffer();

        while(true) {
            byte[] buffer = new byte[1000];
            int i = pis.read(buffer, 0, buffer.length);
            if (i < 0) {
              break;
            }
            saida.append(new String(buffer));
        }

        return saida.toString().trim();

    }

    private String parsePartial(String partial, Object[] params) {
        String out = new String(partial);
        if(params != null && params.length > 0) {
            for(int i = 0; i < params.length; i++) {
                String p = params[i] != null ? params[i].toString() : "";
                out = out.replaceAll("\\{" + i + "\\}", p);
            }
        }
        return out;
    }

    private String getBSColumnStyles(String param) {

        Pattern p = Pattern.compile("(\\d{1,2})");
        Matcher m = p.matcher(param);
        if(m.matches()) {
            return param.replaceAll(p.pattern(), "col-md-$1");

        } else {
            p = Pattern.compile("(\\d{1,2})\\s(\\d{1,2})\\s(\\d{1,2})\\s(\\d{1,2})");
            m = p.matcher(param);
            if(m.matches()) {
                return param.replaceAll(p.pattern(), "col-xs-$1 col-sm-$2 col-md-$3 col-lg-$4");
            }
        }

        return "";
    }

    private String getBSCheckboxOffsetStyles(String param) {

        Pattern p = Pattern.compile("(\\d{1,2})");
        Matcher m = p.matcher(param);
        if(m.matches()) {
            return param.replaceAll(p.pattern(), " col-md-offset-$1");

        } else {
            p = Pattern.compile("(\\d{1,2})\\s(\\d{1,2})\\s(\\d{1,2})\\s(\\d{1,2})");
            m = p.matcher(param);
            if(m.matches()) {
                return param.replaceAll(p.pattern(), " col-xs-offset-$1 col-sm-offset-$2 col-md-offset-$3 col-lg-offset-$4");
            }
        }

        return "";
    }

    public static String findFormLayout(UIComponent component) {

        UIComponent parent = findForm(component);
        if(parent instanceof Form) {
            if(((Form)parent).getFormLayout() != null) {
                return ((Form)parent).getFormLayout();
            } else {
                return BFConstantes.DEFAULT_FORM_LAYOUT;
            }
        }
        return BFConstantes.DEFAULT_FORM_LAYOUT;

    }

    public static UIForm findForm(UIComponent component) {

        UIComponent parent = component.getParent();
        if(parent != null) {

            if(parent instanceof UIForm) {
                return (UIForm)parent;
            } else {
                return findForm(parent);
            }
        }

        return null;

    }
}