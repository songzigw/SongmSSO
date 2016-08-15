package songm.sso.operation;

import io.netty.channel.Channel;
import songm.sso.SSOException;
import songm.sso.entity.Protocol;

public interface Operation {

    public int operation();

    void action(Channel ch, Protocol pro) throws SSOException;

}
