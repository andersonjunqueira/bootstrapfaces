package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.neotech.framework.faces.AbstractMB;
import br.com.neotech.framework.faces.controle.ControleViewMB;

@FacesComponent(value = "neotech.bootstrapfaces.menuitem")
public class MenuItem extends UINamingContainer {

    public enum PropertyKeys {
        text, icon, badgeColor, badge, target
    };

    public boolean isSuperMenuItem() {
        if(getParent() != null && getParent().getNamingContainer() != null) {
            return getParent().getNamingContainer() instanceof MenuItem;
        } else {
            return false;
        }
    }

    public String getBadgeColor() {
        return (String)getStateHelper().eval(PropertyKeys.badgeColor, BFConstantes.DEFAULT_MENU_ITEM_BADGE_COLOR);
    }

    public void setBadgeColor(String badgeColor) {
        getStateHelper().put(PropertyKeys.badgeColor, badgeColor);
    }

    public String getBadge() {
        return (String)getStateHelper().eval(PropertyKeys.badge, null);
    }

    public void setBadge(String badge) {
        getStateHelper().put(PropertyKeys.badge, badge);
    }

    public String getText() {
        return (String)getStateHelper().eval(PropertyKeys.text, null);
    }

    public void setText(String text) {
        getStateHelper().put(PropertyKeys.text, text);
    }

    public String getIcon() {
        return (String)getStateHelper().eval(PropertyKeys.icon, BFConstantes.DEFAULT_MENU_ITEM_ICON);
    }

    public void setIcon(String icon) {
        getStateHelper().put(PropertyKeys.icon, icon);
    }

    public String getTarget() {
        return (String)getStateHelper().eval(PropertyKeys.target, null);
    }

    public void setTarget(String target) {
        getStateHelper().put(PropertyKeys.target, target);
    }

    public void menuItemSelection(ActionEvent e) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ControleViewMB controleMB = (ControleViewMB)AbstractMB.getBean(fc, "controleViewMB", ControleViewMB.class);
        controleMB.setActiveMenuId(e.getComponent().getClientId());
    }

    public boolean isGroupActive() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ControleViewMB controleMB = (ControleViewMB)AbstractMB.getBean(fc, "controleViewMB", ControleViewMB.class);

        if(controleMB.getActiveMenuId() == null) {
            return false;
        }

        return controleMB.getActiveMenuId().startsWith(getClientId());
    }


}