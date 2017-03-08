package br.com.neotech.framework.faces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import br.com.neotech.framework.excecao.NegocioException;

public abstract class AbstractConversationMB extends AbstractMB {

    private static final long serialVersionUID = 3285152160075372103L;

    @Inject
    private Conversation conversation;

    @PostConstruct
    public void init() throws NegocioException {
        beginConversation();
    }

    public void beginConversation() {
       if(conversation.isTransient()) {
           conversation.begin();
       }
    }

    public void endConversation() {
       if(!conversation.isTransient()) {
           conversation.end();
       }
    }

    public String getCid() {
        return this.conversation.getId();
    }

    public void setCid(String cid) {
    }

}
