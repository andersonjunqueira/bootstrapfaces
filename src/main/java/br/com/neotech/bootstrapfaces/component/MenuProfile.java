package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value = "ctis.bootstrapfaces.menuprofile")
public class MenuProfile extends UINamingContainer {

    enum PropertyKeys {
        name, role, showPicture, showMenu, badge
    }

    public String getBadge() {
        return (String)getStateHelper().eval(PropertyKeys.badge, null);
    }

    public void setBadge(String badge) {
        getStateHelper().put(PropertyKeys.badge, badge);
    }

    public Boolean getShowMenu() {
        return (Boolean)getStateHelper().eval(PropertyKeys.showMenu, true);
    }

    public void setShowMenu(Boolean showMenu) {
        getStateHelper().put(PropertyKeys.showMenu, showMenu);
    }

    public String getName() {
        return (String)getStateHelper().eval(PropertyKeys.name, null);
    }

    public void setName(String name) {
        getStateHelper().put(PropertyKeys.name, name);
    }

    public String getRole() {
        return (String)getStateHelper().eval(PropertyKeys.role, null);
    }

    public void setRole(String role) {
        getStateHelper().put(PropertyKeys.role, role);
    }

    public Boolean getShowPicture() {
        return (Boolean)getStateHelper().eval(PropertyKeys.showPicture, false);
    }

    public void setShowPicture(Boolean showPicture) {
        getStateHelper().put(PropertyKeys.showPicture, showPicture);
    }
}