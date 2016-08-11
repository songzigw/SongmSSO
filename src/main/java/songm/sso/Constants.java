package songm.sso;

import io.netty.util.AttributeKey;
import songm.sso.entity.Account;

public interface Constants {

    public static final AttributeKey<Account> KEY_ACCOUNT = AttributeKey
            .valueOf("CLIENT_ACCOUNT");
}
