package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.convert.Converter;

import br.com.neotech.framework.faces.converter.CNPJConverter;
import br.com.neotech.framework.faces.validator.CNPJValidator;

@FacesComponent(value = "ctis.bootstrapfaces.inputTextCNPJ")
public class InputTextCNPJ extends InputTextMask {

    public InputTextCNPJ() {
        addValidator(new CNPJValidator());
    }

    @Override
    public Converter getConverter() {
        return new CNPJConverter();
    }

    @Override
    public String getMask() {
        return (String)getStateHelper().eval(BSPropertyKeys.mask, "09.000.000/0000-00");
    }

    @Override
    public String getPlaceholder() {
        return (String)getStateHelper().eval(BSPropertyKeys.placeholder, "__.___.___/____-__");
    }

}