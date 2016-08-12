package songm.sso;

import io.netty.util.AttributeKey;
import songm.sso.entity.Backstage;
import songm.sso.entity.Session;

public interface Constants {

    public static final AttributeKey<Backstage> KEY_BACKSTAGE = AttributeKey
            .valueOf("key_backstage");
    public static final AttributeKey<Session> KEY_SESSION = AttributeKey
            .valueOf("key_session");
}
