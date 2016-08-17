package songm.sso.operation;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.JsonUtils;
import songm.sso.SSOException;
import songm.sso.entity.Backstage;
import songm.sso.entity.Protocol;
import songm.sso.entity.Session;
import songm.sso.event.SessionEvent;
import songm.sso.event.SessionListener;
import songm.sso.event.SessionListenerManager;
import songm.sso.service.BackstageService;

@Component
public class AuthOperation extends AbstractOperation {

    private final Logger LOG = LoggerFactory.getLogger(AuthOperation.class);

    public static final int OP = 0;
    public static final int OP_REPLY = 1;

    @Autowired
    private BackstageService backstageService;
    @Autowired
    private SessionListenerManager sessionListenerManager;

    @Override
    public int operation() {
        return OP;
    }

    @Override
    public void action(Channel ch, Protocol pro) throws SSOException {
        Backstage back = JsonUtils.fromJson(pro.getBody(), Backstage.class);

        if (backstageService.auth(back)) {
            setBackstage(ch, back);
            LOG.debug("Auth success to Backstage: {}", back.getBackId());

            pro.setBody(JsonUtils.toJson(back).getBytes());
            pro.setOperation(OP_REPLY);
            ch.writeAndFlush(pro);

            addListener(ch);
        } else {
            ch.close().syncUninterruptibly();
            LOG.debug("Auth fail to Backstage: {}", back.getBackId());
        }
    }

    private void addListener(final Channel ch) {
        sessionListenerManager.addListener(new SessionListener() {
            @Override
            public void onCreate(SessionEvent event) {
                Session s = event.getSession();

                Protocol pro = new Protocol();
                pro.setOperation(OP);
                pro.setBody(JsonUtils.toJson(s).getBytes());

                ch.writeAndFlush(pro);

                LOG.debug("On session create... {}", pro);
            }

            @Override
            public void onUpdate(SessionEvent event) {
                Session s = event.getSession();

                Protocol pro = new Protocol();
                pro.setOperation(OP);
                pro.setBody(JsonUtils.toJson(s).getBytes());

                ch.writeAndFlush(pro);

                LOG.debug("On session update... {}", pro);
            }

            @Override
            public void onRemove(SessionEvent event) {
                Session s = event.getSession();

                Protocol pro = new Protocol();
                pro.setOperation(OP);
                pro.setBody(JsonUtils.toJson(s).getBytes());

                ch.writeAndFlush(pro);

                LOG.debug("On session remove... {}", pro);
            }
        });
    }

}
