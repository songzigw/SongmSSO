package songm.sso.operation;

import io.netty.channel.Channel;
import songm.sso.entity.Protocol;

public interface Operation {

    Integer op();

    void action(Channel ctx, Protocol pro) throws Exception;

}
