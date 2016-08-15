package songm.sso.operation;

import io.netty.channel.Channel;
import songm.sso.Constants;
import songm.sso.SSOException;
import songm.sso.entity.Backstage;

public abstract class AbstractOperation implements Operation {

    protected Backstage getBackstage(Channel ch) {
        return ch.attr(Constants.KEY_BACKSTAGE).get();
    }

    protected void setBackstage(Channel ch, Backstage back) {
        ch.attr(Constants.KEY_BACKSTAGE).set(back);
    }

    protected void checkAuth(Channel ch) throws SSOException {
        if(getBackstage(ch) == null) {
            throw new SSOException("Auth failure");
        }
    }

}
