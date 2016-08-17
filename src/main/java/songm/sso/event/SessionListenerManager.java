package songm.sso.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import songm.sso.entity.Session;
import songm.sso.event.SessionEvent.EventType;

@Component
public class SessionListenerManager {

    private Collection<SessionListener> listeners;

    public SessionListenerManager() {
        listeners = new HashSet<SessionListener>();
    }

    /**
     * 添加事件监听
     * 
     * @param listener
     */
    public void addListener(SessionListener listener) {
        if (listeners == null) {
            listeners = new HashSet<SessionListener>();
        }
        listeners.add(listener);
    }

    /**
     * 移除事件监听
     * 
     * @param listener
     */
    public void removeListener(SessionListener listener) {
        if (listeners == null)
            return;
        listeners.remove(listener);
    }

    private void notifyListeners(SessionEvent event) {
        Iterator<SessionListener> iter = listeners.iterator();
        EventType type = event.getSource();
        while (iter.hasNext()) {
            SessionListener listener = (SessionListener) iter.next();
            if (EventType.CREATE.equals(type)) {
                listener.onCreate(event);
            } else if (EventType.UPDATE.equals(type)) {
                listener.onUpdate(event);
            } else if (EventType.UPDATE.equals(type)) {
                listener.onRemove(event);
            }
        }
    }

    public void triggerCreate(Session session) {
        if (listeners == null)
            return;
        SessionEvent event = new SessionEvent(EventType.CREATE, session);
        this.notifyListeners(event);
    }

    public void triggerUpdate(Session session) {
        if (listeners == null)
            return;
        SessionEvent event = new SessionEvent(EventType.UPDATE, session);
        this.notifyListeners(event);
    }

    public void triggerRemove(Session session) {
        if (listeners == null)
            return;
        SessionEvent event = new SessionEvent(EventType.REMOVE, session);
        this.notifyListeners(event);
    }
}
