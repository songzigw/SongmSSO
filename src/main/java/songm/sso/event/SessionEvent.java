package songm.sso.event;

import java.util.EventObject;

import songm.sso.entity.Session;

public class SessionEvent extends EventObject {

    private static final long serialVersionUID = 1653489079814920063L;

    private Session session;

    public SessionEvent(EventType source, Session session) {
        super(source);
        this.session = session;
    }

    @Override
    public EventType getSource() {
        return (EventType) super.getSource();
    }

    public Session getSession() {
        return session;
    }

    public static enum EventType {
        CREATE, UPDATE, REMOVE
    }
}
