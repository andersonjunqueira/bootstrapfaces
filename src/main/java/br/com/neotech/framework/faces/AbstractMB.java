package br.com.neotech.framework.faces;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Managed Bean Generico com a definição de operações básicas.
 */
public abstract class AbstractMB implements Serializable {

    private static final long serialVersionUID = 2628117946616807375L;

    public static void redirect(String pagina) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            String url = ec.encodeActionURL(fc.getApplication().getViewHandler().getActionURL(fc, pagina));
            ec.redirect(url);
        } catch(IOException ex) {
            adicionaMensagemErro(Mensagens.SIS.get("sistema.erro.problemaredirecionamento"));
        }
    }

    private void download(String nomeArquivo, InputStream dados, String mimeType) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        download(nomeArquivo, dados, mimeType, response);
    }

    public void download(String nomeArquivo, InputStream dados, String mimeType, HttpServletResponse response)
        throws IOException {

        response.resetBuffer();
        response.reset();
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");

        InputStream is = dados;
        OutputStream os = response.getOutputStream();
        try {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();

        } finally {
            is.close();
        }

        response.flushBuffer();

    }

    protected void downloadFromByteArray(String nomeArquivo, byte[] dados, String mimeType) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(dados);
        download(nomeArquivo, is, mimeType);
    }

    protected void downloadFromByteArray(String nomeArquivo, byte[] dados) throws IOException {
        downloadFromByteArray(nomeArquivo, dados, "application/octet-stream");
    }

    protected void downloadFromContext(String nomeArquivo, String caminhoContexto, String mimeType) throws IOException {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ServletContext sctx = (ServletContext)fctx.getExternalContext().getContext();
        InputStream is = sctx.getResourceAsStream(caminhoContexto);
        download(nomeArquivo, is, mimeType);
    }

    protected void downloadFromContext(String nomeArquivo, String caminhoContexto) throws IOException {
        downloadFromContext(nomeArquivo, caminhoContexto, "application/octet-stream");
    }

    protected void downloadFromFilesystem(String nomeArquivo, String caminhoArquivo, String mimeType) throws IOException {
        FileInputStream fis = new FileInputStream(caminhoArquivo);
        download(nomeArquivo, fis, mimeType);
    }

    protected void downloadFromFilesystem(String nomeArquivo, String caminhoArquivo) throws IOException {
        downloadFromFilesystem(nomeArquivo, caminhoArquivo, "application/octet-stream");
    }

    public static void adicionaMensagem(FacesMessage.Severity tipo, String componente, String mensagem, String detalhe) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(mensagem, detalhe);
        fm.setSeverity(tipo);
        fc.addMessage(componente, fm);
    }

    public static void adicionaMensagem(FacesMessage.Severity tipo, String mensagem) {
        adicionaMensagem(tipo, null, mensagem, null);
    }

    public static void adicionaMensagemErro(String componente, String mensagem) {
        adicionaMensagem(FacesMessage.SEVERITY_ERROR, componente, mensagem, null);
    }

    public static void adicionaMensagemErro(String mensagem) {
        adicionaMensagemErro(null, mensagem);
    }

    public static void adicionaMensagemInfo(String componente, String mensagem) {
        adicionaMensagem(FacesMessage.SEVERITY_INFO, componente, mensagem, null);
    }

    public static void adicionaMensagemInfo(String mensagem) {
        adicionaMensagemInfo(null, mensagem);
    }

    public static void adicionaMensagemAviso(String componente, String mensagem) {
        adicionaMensagem(FacesMessage.SEVERITY_WARN, componente, mensagem, null);
    }

    public static void adicionaMensagemAviso(String mensagem) {
        adicionaMensagemAviso(null, mensagem);
    }

    public static void adicionaMensagemFatal(String componente, String mensagem) {
        adicionaMensagem(FacesMessage.SEVERITY_FATAL, componente, mensagem, null);
    }

    public static void adicionaMensagemFatal(String mensagem) {
        adicionaMensagemFatal(null, mensagem);
    }

    public static Object getBean(FacesContext fctx, String elName, Class<?> clazz) {
        String exp = "#{" + elName + "}";
        return fctx.getApplication().evaluateExpressionGet(fctx, exp, clazz);
    }

    public static void adicionarCookie(HttpServletResponse response, String cookieName, int cookieAge, String conteudo) {
        Cookie cookie = new Cookie(cookieName, conteudo);
        cookie.setMaxAge(cookieAge);
        response.addCookie(cookie);
    }

    public static void adicionarCookie(String cookieName, int cookieAge, String conteudo) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();
        adicionarCookie(response, cookieName, cookieAge, conteudo);
    }

    public static void removerCookie(HttpServletResponse response, String cookieName) {
        adicionarCookie(response, cookieName, 0, "");
    }

    public static void removerCookie(String cookieName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();

        Cookie[] cks = request.getCookies();
        if(cks != null) {
            for(Cookie ck : cks) {
                if (ck.getName().equals(cookieName)) {
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

    public static String carregarCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cks = request.getCookies();
        if(cks != null) {
            for(Cookie ck : cks) {
                if (ck.getName().equals(cookieName)) {
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static String carregarCookie(String cookieName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
        return carregarCookie(request, cookieName);
    }

    protected String getAppUrl() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        String contexto = url.replaceAll("(http://([^/]+/){2}).+","$1");
        return contexto;
    }

}
