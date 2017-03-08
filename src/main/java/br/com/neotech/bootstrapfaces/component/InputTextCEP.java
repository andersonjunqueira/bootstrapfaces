package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.convert.Converter;

import br.com.neotech.framework.faces.converter.CEPConverter;
import br.com.neotech.framework.faces.validator.CEPValidator;

@FacesComponent(value = "ctis.bootstrapfaces.inputTextCEP")
public class InputTextCEP extends InputTextMask {

    public InputTextCEP() {
        addValidator(new CEPValidator());
    }

    @Override
    public Converter getConverter() {
        return new CEPConverter();
    }

    @Override
    public String getMask() {
        return (String)getStateHelper().eval(BSPropertyKeys.mask, "00.000-000");
    }

    @Override
    public String getPlaceholder() {
        return (String)getStateHelper().eval(BSPropertyKeys.placeholder, "__.___-___");
    }
}