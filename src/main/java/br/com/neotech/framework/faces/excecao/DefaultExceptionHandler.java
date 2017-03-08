package br.com.neotech.framework.faces.excecao;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.sun.faces.context.FacesFileNotFoundException;

import br.com.neotech.framework.excecao.AutorizacaoException;
import br.com.neotech.framework.excecao.NegocioException;
import br.com.neotech.framework.excecao.ServicoRemotoException;
import br.com.neotech.framework.faces.AbstractMB;
import br.com.neotech.framework.faces.Mensagens;
import br.com.neotech.framework.faces.controle.ControleViewMB;
import br.com.neotech.framework.util.Constantes;
import lombok.extern.log4j.Log4j;

@Log4j
public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;
    protected FacesContext fc;
    protected NavigationHandler nav;

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    /**
     * Extrai a stacktrace da exceção.
     *
     * @param t - {@link Throwable} Exception
     * @return - {@link String} Stack Trace
     */
    private String getStackTrace(Throwable t) {
        StringBuilder builder = new StringBuilder();
        builder.append(t.toString()).append("<br/>");
        for (StackTraceElement element : t.getStackTrace()) {
            builder.append(element).append("<br/>");
        }
        return builder.toString();
    }

    @Override
    public void handle() throws FacesException {
        // TRATAMENTO DAS EXCEÇÕES
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {

            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();

            while ((t instanceof FacesException || t instanceof EJBException || t instanceof ELException) && t.getCause() != null) {
               t = t.getCause();
            }

            try {

                log.error("ERRO!", t);

                // EXCECAO DE AUTORIZACAO
                if (t instanceof AutorizacaoException) {
                    handleAutorizacao(t);

                // EXCEÇÕES CONTROLADAS PELA APLICAÇÃO
                } else if (t instanceof NegocioException ||
                    t instanceof ServicoRemotoException) {

                    handleNegocio(t);

                // SESSÃO EXPIRADA
                } else if (t instanceof ViewExpiredException) {

                    handleViewExpired(t);

                // DATABASE EXCEPTION
                } else if (t.getClass().getName().contains("RollbackException")) {

                    Throwable t1 = t.getCause();
                    while(t1.getCause() != null) {
                        t1 = t1.getCause();
                    }

                    handleJPA(t1);

                // CONVERSAÇÃO NÃO ENCONTRADA
                } else if(t instanceof NonexistentConversationException) {
                    handleCIDExpired(t);

                // ARQUIVO NÃO ENCONTRADO
                } else if(t instanceof FacesFileNotFoundException) {
                    ResourceBundle r = ResourceBundle.getBundle("SistemaMessages", Locale.getDefault());
                    handle404(t, "404", r.getString("sistema.errorpage.paginanaoencontrada"));

                // FACES EXCEPTION
                } else if (t instanceof FacesException) {
                    handleGeneric(t, "Ops...");

                // OUTRAS EXCEÇÕES
                } else {
                    handleGeneric(t, "Ops...");
                }

            } finally {
                i.remove();
            }
        }
        getWrapped().handle();
    }

    public void handleGeneric(Throwable t, String codigo) {
        handleGeneric(t, codigo, Mensagens.SIS.get("sistema.errorpage.instrucao"));
    }

    public void handleGeneric(Throwable t, String codigo, String mensagem) {

        FacesContext fc = FacesContext.getCurrentInstance();
        String contextRoot = fc.getExternalContext().getRequestContextPath();
        UIViewRoot vr = fc.getViewRoot();

        ControleViewMB controleMB = fc.getApplication().evaluateExpressionGet(fc, "#{controleViewMB}", ControleViewMB.class);

        controleMB.setCodigoErro(codigo);
        controleMB.setMensagemErro(mensagem);
        controleMB.setCausaErro(t.getMessage() != null ? t.getMessage() : t.toString());
        controleMB.setStackErro(getStackTrace(t));
        if(vr != null) {
            controleMB.setCurrentViewId(fc.getViewRoot().getViewId());
        }

        try {
            fc.getExternalContext().redirect(contextRoot + Constantes.URL_ERRO);
        } catch(IOException ioex) {
            NavigationHandler nav = fc.getApplication().getNavigationHandler();
            nav.handleNavigation(fc, null, Constantes.URL_ERRO);
            fc.renderResponse();
        }

    }

    public void handle404(Throwable t, String codigo, String mensagem) {

        FacesContext fc = FacesContext.getCurrentInstance();
        String contextRoot = fc.getExternalContext().getRequestContextPath();

        ControleViewMB controleMB = fc.getApplication().evaluateExpressionGet(fc, "#{controleViewMB}", ControleViewMB.class);

        controleMB.setCodigoErro(codigo);
        controleMB.setMensagemErro(mensagem);

        try {
            fc.getExternalContext().redirect(contextRoot + Constantes.URL_ERRO);
        } catch(IOException ioex) {
            NavigationHandler nav = fc.getApplication().getNavigationHandler();
            nav.handleNavigation(fc, null, Constantes.URL_ERRO);
            fc.renderResponse();
        }

    }

    public void handleAutorizacao(Throwable t) {

        FacesContext fc = FacesContext.getCurrentInstance();
        ControleViewMB controleMB = fc.getApplication().evaluateExpressionGet(fc, "#{controleViewMB}", ControleViewMB.class);

        ViewExpiredException vee = (ViewExpiredException) t;
        controleMB.setCurrentViewId(vee.getViewId());
        AbstractMB.adicionaMensagemAviso(Mensagens.SIS.get("sistema.erro.usuarionaoautorizado"));

        NavigationHandler nav = fc.getApplication().getNavigationHandler();
        nav.handleNavigation(fc, null, Constantes.URL_HOME);
        fc.renderResponse();

    }

    public void handleNegocio(Throwable t) {

        Mensagens bundle = t.getMessage().startsWith("sistema.") ? Mensagens.SIS : Mensagens.MSG;
        AbstractMB.adicionaMensagemErro(bundle.get(t.getMessage()));
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.renderResponse();

    }

    public void handleCIDExpired(Throwable t) {

        FacesContext fc = FacesContext.getCurrentInstance();
        AbstractMB.adicionaMensagemAviso(Mensagens.SIS.get("sistema.erro.cidexpirada"));

        NavigationHandler nav = fc.getApplication().getNavigationHandler();
        nav.handleNavigation(fc, null, Constantes.URL_HOME);
        fc.renderResponse();

    }

    public void handleViewExpired(Throwable t) {

        FacesContext fc = FacesContext.getCurrentInstance();
        AbstractMB.adicionaMensagemAviso(Mensagens.SIS.get("sistema.erro.sessaoexpirada"));

        if(t instanceof ViewExpiredException) {
            ControleViewMB controleMB = fc.getApplication().evaluateExpressionGet(fc, "#{controleViewMB}", ControleViewMB.class);
            ViewExpiredException vee = (ViewExpiredException) t;
            controleMB.setCurrentViewId(vee.getViewId());
        }

        NavigationHandler nav = fc.getApplication().getNavigationHandler();
        nav.handleNavigation(fc, null, Constantes.URL_HOME);
        fc.renderResponse();

    }

    public void handleJPA(Throwable t) {

        String msg = null;
        if(t.toString().contains("ORA-02292")) {
            msg = Mensagens.SIS.get("sistema.erro.registrorelacionado");
        }

        if(msg == null) {
            msg = Mensagens.SIS.get("sistema.errorpage.errogeral");
        }

        AbstractMB.adicionaMensagemErro(msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.renderResponse();

    }
}
