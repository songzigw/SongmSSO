package songm.sso.operation;

import io.netty.channel.Channel;
import songm.sso.Constants;
import songm.sso.entity.Account;
import songm.sso.exception.UnauthorizedException;

public abstract class AbstractOperation implements Operation {

    protected Account getAccount(Channel ch) {
        return ch.attr(Constants.KEY_ACCOUNT).get();
    }

    protected void setAccount(Channel ch, Account acc) {
        ch.attr(Constants.KEY_ACCOUNT).set(acc);
    }

    protected void checkAuth(Channel ch) throws UnauthorizedException {
        if(getAccount(ch) == null) {
            throw new UnauthorizedException("Auth failure");
        }
    }

}
