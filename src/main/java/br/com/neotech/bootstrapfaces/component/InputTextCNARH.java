package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.convert.Converter;

import br.com.neotech.framework.faces.converter.CNARHConverter;
import br.com.neotech.framework.faces.validator.CNARHValidator;

@FacesComponent(value = "neotech.bootstrapfaces.inputTextCNARH")
public class InputTextCNARH extends InputTextMask {

    public InputTextCNARH() {
        addValidator(new CNARHValidator());
    }

    @Override
    public Converter getConverter() {
        return new CNARHConverter();
    }

    @Override
    public String getMask() {
        return (String)getStateHelper().eval(BSPropertyKeys.mask, "00.0.0000000/00");
    }

    @Override
    public String getPlaceholder() {
        return (String)getStateHelper().eval(BSPropertyKeys.placeholder, "__._._______/__");
    }

}