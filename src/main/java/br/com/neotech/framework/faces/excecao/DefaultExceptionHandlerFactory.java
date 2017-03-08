package br.com.neotech.framework.faces.excecao;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class DefaultExceptionHandlerFactory extends ExceptionHandlerFactory {

    protected ExceptionHandlerFactory parent;

    public DefaultExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new DefaultExceptionHandler(result);
        return result;
    }

}
