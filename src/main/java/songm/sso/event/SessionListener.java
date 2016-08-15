package songm.sso.event;

import java.util.EventListener;

public interface SessionListener extends EventListener {

    void onCreate(SessionEvent event);

    void onUpdate(SessionEvent event);

    void onRemove(SessionEvent event);
}