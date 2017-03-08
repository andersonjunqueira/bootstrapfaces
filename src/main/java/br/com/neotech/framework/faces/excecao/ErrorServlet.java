package br.com.neotech.framework.faces.excecao;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.neotech.framework.faces.AbstractPhaseListener;
import br.com.neotech.framework.faces.controle.ControleViewMB;
import br.com.neotech.framework.util.Constantes;

@WebServlet("/error-handler")
public class ErrorServlet extends HttpServlet {

    private static final long serialVersionUID = 5061827938346807589L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ResourceBundle r = ResourceBundle.getBundle("SistemaMessages", Locale.getDefault());

        Exception exception = (Exception)req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)req.getAttribute("javax.servlet.error.status_code");
        // String servletName = (String)req.getAttribute("javax.servlet.error.servlet_name");
        String requestUri = (String)req.getAttribute("javax.servlet.error.request_uri");

        ControleViewMB controleMB = AbstractPhaseListener.getBeanByName("controleViewMB");
        controleMB.setCodigoErro(statusCode.toString());
        controleMB.setCurrentViewId(requestUri);

        String mensagem = null;
        switch (statusCode) {
            case 404:
                mensagem = r.getString("sistema.errorpage.paginanaoencontrada");
                break;
            case 500:
                mensagem = r.getString("sistema.errorpage.errogeral");
                break;
        }
        controleMB.setMensagemErro(mensagem);

        if(exception != null) {
            controleMB.setCausaErro(exception.getMessage() != null ? exception.getMessage() : exception.toString());
            controleMB.setStackErro(getStackTrace(exception));
        }

        String contextRoot = req.getContextPath();
        resp.sendRedirect(contextRoot + Constantes.URL_ERRO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private String getStackTrace(Throwable t) {
        StringBuilder builder = new StringBuilder();
        builder.append(t.toString()).append("<br/>");
        for (StackTraceElement element : t.getStackTrace()) {
            builder.append(element).append("<br/>");
        }
        return builder.toString();
    }

}