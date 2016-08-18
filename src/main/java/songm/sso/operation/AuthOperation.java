/*
 * Copyright [2016] [zhangsong <songm.cn>].
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

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

    @Autowired
    private BackstageService backstageService;
    @Autowired
    private SessionListenerManager sessionListenerManager;

    @Override
    public int operation() {
        return Type.AUTH_REQUEST.getValue();
    }

    @Override
    public void action(Channel ch, Protocol pro) throws SSOException {
        Backstage back = JsonUtils.fromJson(pro.getBody(), Backstage.class);

        if (backstageService.auth(back)) {
            // 授权成功
            LOG.debug("Auth success to Backstage: {}", back.getBackId());
            setBackstage(ch, back);

            pro.setOperation(Type.AUTH_SUCCEED.getValue());
            pro.setBody(JsonUtils.toJson(back).getBytes());
            ch.writeAndFlush(pro);

            addListener(ch);
        } else {
            // 授权失败
            LOG.debug("Auth fail to Backstage: {}", back.getBackId());

            pro.setOperation(Type.AUTH_FAIL.getValue());
            pro.setBody(JsonUtils.toJson(back).getBytes());
            ch.writeAndFlush(pro);

            // 关闭连接
            ch.close().syncUninterruptibly();
        }
    }

    private void addListener(final Channel ch) {
        sessionListenerManager.addListener(new SessionListener() {
            @Override
            public void onCreate(SessionEvent event) {
                Session s = event.getSession();

                Protocol pro = new Protocol();
                pro.setOperation(Type.SESSION_CREATE.getValue());
                pro.setBody(JsonUtils.toJson(s).getBytes());

                ch.writeAndFlush(pro);

                LOG.debug("On session create... {}", pro);
            }

            @Override
            public void onUpdate(SessionEvent event) {
                Session s = event.getSession();

                Protocol pro = new Protocol();
                pro.setOperation(Type.SESSION_UPDATE.getValue());
                pro.setBody(JsonUtils.toJson(s).getBytes());

                ch.writeAndFlush(pro);

                LOG.debug("On session update... {}", pro);
            }

            @Override
            public void onRemove(SessionEvent event) {
                Session s = event.getSession();

                Protocol pro = new Protocol();
                pro.setOperation(Type.SESSION_REMOVE.getValue());
                pro.setBody(JsonUtils.toJson(s).getBytes());

                ch.writeAndFlush(pro);

                LOG.debug("On session remove... {}", pro);
            }
        });
    }

}
