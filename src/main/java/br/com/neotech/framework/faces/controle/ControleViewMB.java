package br.com.neotech.framework.faces.controle;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.neotech.framework.config.ConfigCache;
import br.com.neotech.framework.excecao.ExceptionController;
import br.com.neotech.framework.faces.AbstractMB;
import br.com.neotech.framework.faces.ViewHelper;
import br.com.neotech.framework.util.Constantes;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Named
@SessionScoped
public class ControleViewMB extends AbstractMB implements ExceptionController, Serializable {

    private static final long serialVersionUID = -4558501970279758988L;

    @Getter @Setter
    private String activeMenuId;

    private Locale locale;

    @Getter
    private ViewHelper helper = new ViewHelper();

    @Getter @Setter
    private String currentViewId;

    @Getter @Setter
    private String codigoErro;

    @Getter @Setter
    private String mensagemErro;

    @Getter @Setter
    private String causaErro;

    @Getter @Setter
    private String stackErro;

    @Getter @Setter
    private String pagina;

    // === MECANISMO DE APRESENTAÇÃO DO AVISO
    private boolean avisoVisto;
    private String mensagemAviso;

    public boolean getPossuiAviso() {

        mensagemAviso = ConfigCache.getInstance().get("sistema.geral.aviso");
        String inicio = ConfigCache.getInstance().get("sistema.geral.aviso.inicio");
        String fim = ConfigCache.getInstance().get("sistema.geral.aviso.fim");

        boolean datasOk = true;
        if(inicio != null && fim != null) {
            try {
                SimpleDateFormat df = new SimpleDateFormat();
                df.applyPattern("dd/MM/yyyy HH:mm:ss");
                Date dtInicio = df.parse(inicio);
                Date dtFim = df.parse(fim);
                Date agora = new Date(System.currentTimeMillis());
                datasOk = agora.after(dtInicio) && agora.before(dtFim);
            } catch (ParseException e) {
                log.error(e);
            }
        }

        return datasOk && (mensagemAviso != null);
    }

    public String getMensagemAviso() {
        return mensagemAviso;
    }

    public boolean getAvisoVisto() {
        return avisoVisto;
    }

    public void marcarAvisoVisto() {
        avisoVisto = true;
    }
    // === FIM/MECANISMO DE APRESENTACAO DE AVISO

    public void defineIdioma(String lang) {
        locale = new Locale(lang);
        setIdioma(locale);
    }

    public void defineIdioma(String lang, String country) {
        locale = new Locale(lang, country);
        setIdioma(locale);
    }

    public void logout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.invalidateSession();
        AbstractMB.redirect(Constantes.URL_HOME);
    }

    private void setIdioma(Locale loc) {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(loc);
        Locale.setDefault(loc);
    }

    public String getIdioma() {
        if(locale == null) {
           defineIdioma("pt", "BR");
        }
        return locale.getLanguage();
    }

    public Locale getLocale() {
        return locale;
    }
}