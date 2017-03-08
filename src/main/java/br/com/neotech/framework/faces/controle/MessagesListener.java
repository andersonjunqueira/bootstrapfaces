package br.com.neotech.framework.faces.controle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.neotech.framework.faces.AbstractMB;
import br.com.neotech.framework.faces.Mensagens;

public class MessagesListener implements PhaseListener {

    private static final long serialVersionUID = -5377907800488522107L;

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();

        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Iterator<String> cids = context.getClientIdsWithMessages();
        while (cids.hasNext()) {
            String cid = cids.next();
            if(cid != null) {
                msgs.addAll(context.getMessageList(cid));
            }
        }

        boolean haveMessage = msgs.size() > 0;
        if(haveMessage) {
            FacesMessage.Severity sev = null;
            for(FacesMessage fm : msgs) {
                if(sev == null) {
                    sev = fm.getSeverity();
                } else if(fm.getSeverity() == FacesMessage.SEVERITY_INFO) {
                    sev = fm.getSeverity();
                } else if(fm.getSeverity() == FacesMessage.SEVERITY_WARN && sev == FacesMessage.SEVERITY_INFO) {
                    sev = fm.getSeverity();
                } else if(fm.getSeverity() == FacesMessage.SEVERITY_ERROR && sev == FacesMessage.SEVERITY_WARN) {
                    sev = fm.getSeverity();
                } else if(fm.getSeverity() == FacesMessage.SEVERITY_FATAL && sev == FacesMessage.SEVERITY_ERROR) {
                    sev = fm.getSeverity();
                }
            }

            AbstractMB.adicionaMensagem(sev, Mensagens.COMPONENTES.get("framework.mensagens.atencao"));
        }

    }

    @Override
    public void afterPhase(PhaseEvent event) {
        // Do your job here which should run right after the RENDER_RESPONSE.
    }

}