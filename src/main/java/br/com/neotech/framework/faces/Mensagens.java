package br.com.neotech.framework.faces;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import br.com.neotech.framework.util.Resources;

/**
 * Classe que encapsula o comportamento de recuperação de strings nos arquivos de propriedades do sistema.
 * @author AJ
 *
 */
public class Mensagens extends Resources {

    public static Mensagens SIS = new Mensagens("sis", false);
	public static Mensagens MSG = new Mensagens("msg", false);
    public static Mensagens COMPONENTES = new Mensagens("comps", false);
    public static Mensagens MESSAGES = new Mensagens("comps", true);

    private final boolean defaultJSFBundle;

    private Mensagens(String bundleName, boolean defaultJSFBundle) {
        super(bundleName);
        this.defaultJSFBundle = defaultJSFBundle;
    }

    @Override
    protected ResourceBundle getBundle() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Locale locale = fc.getViewRoot() != null ? fc.getViewRoot().getLocale() : new Locale("pt", "BR");

        if(defaultJSFBundle) {
            return ResourceBundle.getBundle(fc.getApplication().getMessageBundle(), locale);
        } else {
            return fc.getApplication().getResourceBundle(fc, getBundleName());
        }
    }


}

