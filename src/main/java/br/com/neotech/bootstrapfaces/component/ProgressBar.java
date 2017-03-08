package br.com.neotech.bootstrapfaces.component;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("neotech.bootstrapfaces.progressBar")
public class ProgressBar extends UINamingContainer {

    enum PropertyKeys {
        position, size, color
    }

    public Double getPosition() {
        return (Double)getStateHelper().eval(PropertyKeys.position, null);
    }

    public void setPosition(Double position) {
        getStateHelper().put(PropertyKeys.position, position);
    }

    public Integer getSize() {
        return (Integer)getStateHelper().eval(PropertyKeys.size, 3);
    }

    public void setSize(Integer size) {
        getStateHelper().put(PropertyKeys.size, size);
    }

    public String getColor() {
        return (String)getStateHelper().eval(PropertyKeys.color, "primary");
    }

    public void setColor(String color) {
        getStateHelper().put(PropertyKeys.color, color);
    }

    public String getSizeClass() {
        String saida = "";

        Integer size = getSize();
        if(size == 2) {
            saida += " progress-sm";
        } else if(size == 1) {
            saida += " progress-xs";
        }

        return saida;
    }
}