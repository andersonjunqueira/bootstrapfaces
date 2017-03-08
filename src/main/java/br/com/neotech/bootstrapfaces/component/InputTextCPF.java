package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.convert.Converter;

import br.com.neotech.framework.faces.converter.CPFConverter;
import br.com.neotech.framework.faces.validator.CPFValidator;

@FacesComponent(value = "ctis.bootstrapfaces.inputTextCPF")
public class InputTextCPF extends InputTextMask {

    public InputTextCPF() {
        addValidator(new CPFValidator());
    }

    @Override
    public Converter getConverter() {
        return new CPFConverter();
    }

    @Override
    public String getMask() {
        return (String)getStateHelper().eval(BSPropertyKeys.mask, "000.000.000-00");
    }

    @Override
    public String getPlaceholder() {
        return (String)getStateHelper().eval(BSPropertyKeys.placeholder, "___.___.___-__");
    }
}