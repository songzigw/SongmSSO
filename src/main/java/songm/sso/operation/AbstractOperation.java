package songm.sso.operation;

import io.netty.channel.Channel;
import songm.sso.Constants;
import songm.sso.entity.Account;

public abstract class AbstractOperation implements Operation {

    protected Account getAccount(Channel ch) {
        return ch.attr(Constants.KEY_ACCOUNT).get();
    }

    protected void setAccount(Channel ch, Account acc) {
        ch.attr(Constants.KEY_ACCOUNT).set(acc);
    }

    protected void checkAuth(Channel ch) {

    }

}
