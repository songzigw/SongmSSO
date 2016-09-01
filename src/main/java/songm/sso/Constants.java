package songm.sso;

import io.netty.util.AttributeKey;
import songm.sso.entity.Backstage;

public interface Constants {

    public static final AttributeKey<Backstage> KEY_BACKSTAGE = AttributeKey
            .valueOf("key_backstage");
}
