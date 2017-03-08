package br.com.neotech.bootstrapfaces.component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent("ctis.bootstrapfaces.tabPanel")
public class TabPanel extends UINamingContainer {

    enum PropertyKeys {
        value, renderTarget
    }

    private Map<String, TabHeader> header;
    private Map<String, TabContent> content;

    public void encodeHeaders() throws IOException {
        preparaComponentes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        for(UIComponent c : getChildren()) {
            if(c instanceof TabHeader) {
                c.encodeAll(ctx);
            }
        }
    }

    public void encodeContent() throws IOException {
        preparaComponentes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        for(UIComponent c : getChildren()) {
            if(c instanceof TabContent) {
                TabHeader h = header.get(((TabContent)c).getJsId());
                if(h != null && h.getActive()) {
                    ((TabContent)c).setActive(true);
                } else {
                    ((TabContent)c).setActive(false);
                }
                c.encodeAll(ctx);
            }
        }
    }

    private void preparaComponentes() {
        if(getChildCount() > 0) {
            if(header == null) {
                header = new HashMap<String, TabHeader>();
                for(UIComponent c : getChildren()) {
                    if(c instanceof TabHeader) {
                        header.put(((TabHeader)c).getJsId(), (TabHeader)c);
                    }
                }
            }

            if(content == null) {
                content = new HashMap<String, TabContent>();
                for(UIComponent c : getChildren()) {
                    if(c instanceof TabContent) {
                        content.put(((TabContent)c).getJsId(), (TabContent)c);
                    }
                }
            }
        }
    }

}
