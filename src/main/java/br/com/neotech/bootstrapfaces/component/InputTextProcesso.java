package br.com.neotech.bootstrapfaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.convert.Converter;

import br.com.neotech.framework.faces.converter.NumeroProcessoConverter;

@FacesComponent(value = "neotech.bootstrapfaces.inputTextProcesso")
public class InputTextProcesso extends InputTextMask {

    public InputTextProcesso() {
    }

    @Override
    public Converter getConverter() {
        return new NumeroProcessoConverter();
    }

    @Override
    public String getMask() {
        return (String)getStateHelper().eval(BSPropertyKeys.mask, "00000.000000/0000-00");
    }

    @Override
    public String getPlaceholder() {
        return (String)getStateHelper().eval(BSPropertyKeys.placeholder, "_____.______/____-__");
    }
}