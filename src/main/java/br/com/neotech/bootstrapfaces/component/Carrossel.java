package br.com.neotech.bootstrapfaces.component;

import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value = "neotech.bootstrapfaces.carrossel")
public class Carrossel extends UINamingContainer {

    public enum BSPropertyKeys {
        value, width, height
    };

    @SuppressWarnings("unchecked")
    public List<String> getValue() {
        return (List<String>)getStateHelper().eval(BSPropertyKeys.value, null);
    }

    public void setValue(String value) {
        getStateHelper().put(BSPropertyKeys.value, value);
    }

    public Integer getWidth() {
        return (Integer)getStateHelper().eval(BSPropertyKeys.width, null);
    }

    public void setWidth(Integer width) {
        getStateHelper().put(BSPropertyKeys.width, width);
    }

    public Integer getHeight() {
        return (Integer)getStateHelper().eval(BSPropertyKeys.height, null);
    }

    public void setHeight(Integer height) {
        getStateHelper().put(BSPropertyKeys.height, height);
    }

    public int getSize() {
        return getValue() != null ? getValue().size() : 0;
    }

}