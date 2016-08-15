package songm.sso.event;

import java.util.EventObject;

import songm.sso.entity.Session;

public class SessionEvent extends EventObject {

    private static final long serialVersionUID = 1653489079814920063L;

    public static final int CREATE = 1;
    public static final int UPDATE = 2;
    public static final int REMOVE = 3;

    private int type;

    public SessionEvent(Session source, int eventType) {
        super(source);
        this.type = eventType;
    }

    public int getType() {
        return type;
    }
}
