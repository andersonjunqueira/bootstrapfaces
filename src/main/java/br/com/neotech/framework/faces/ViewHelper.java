package br.com.neotech.framework.faces;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ViewHelper {

    private boolean verificado;

    private boolean ie;
    private boolean firefox;
    private boolean chrome;
    private boolean outros;

    private double versao;

    private void verificar() {
        if(!verificado) {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            String userAgent = ec.getRequestHeaderMap().get("User-Agent");

            try {
                firefox = userAgent.contains("Firefox");
                if(firefox) {
                    versao = Double.valueOf(userAgent.replaceAll(".+Firefox\\/(\\d+\\.\\d+).*", "$1"));

                } else  {

                    chrome = userAgent.contains("Chrome");
                    if(chrome) {
                        versao = Double.valueOf(userAgent.replaceAll(".+Chrome\\/(\\d+\\.\\d+).*", "$1"));

                    } else {

                        // INTERNET EXPLORER ATÉ A VERSAO 10
                        ie = userAgent.contains("MSIE");
                        if(ie) {
                            versao = Double.valueOf(userAgent.replaceAll(".+;\\sMSIE\\s(\\d+\\.\\d+);.+", "$1"));

                        } else {
                            // INTERNET EXPLORER A PARTIR DA VERSAO 11
                            versao = Double.valueOf(userAgent.replaceAll(".+Trident.+rv\\:(\\d+\\.\\d+).+", "$1"));
                            ie = versao > 0D;
                        }
                    }
                }

                outros = !ie && !firefox && !chrome;

            } catch(NumberFormatException ex) {
                outros = true;
            }

            verificado = true;
        }
    }

    public boolean isIE() {
        verificar();
        return ie;
    }

    public boolean isFirefox() {
        verificar();
        return firefox;
    }

    public boolean isChrome() {
        verificar();
        return chrome;
    }

    public boolean isOutros() {
        verificar();
        return outros;
    }

    public double getVersao() {
        verificar();
        return versao;
    }

}
