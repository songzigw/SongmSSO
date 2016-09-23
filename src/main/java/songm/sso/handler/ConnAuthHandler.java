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
package songm.sso.handler;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.SSOException.ErrorCode;
import songm.sso.entity.Backstage;
import songm.sso.entity.Protocol;
import songm.sso.event.SessionListener;
import songm.sso.event.SessionListenerManager;
import songm.sso.service.BackstageService;
import songm.sso.utils.JsonUtils;

/**
 * 连接授权操作
 * @author zhangsong
 *
 */
@Component("connAuthHandler")
public class ConnAuthHandler extends AbstractHandler {

    private final Logger LOG = LoggerFactory.getLogger(ConnAuthHandler.class);

    @Autowired
    private BackstageService backstageService;
    @Autowired
    private SessionListenerManager sessionListenerManager;

    @Override
    public int operation() {
        return Operation.CONN_AUTH.getValue();
    }

    @Override
    public void action(Channel ch, Protocol pro) {
        Backstage back = JsonUtils.fromJson(pro.getBody(), Backstage.class);

        if (backstageService.auth(back)) {
            // 授权成功
            LOG.debug("Auth success to Backstage: {}", back.getBackId());
            setBackstage(ch, back);

            pro.setBody(JsonUtils.toJson(back, Backstage.class).getBytes());
            ch.writeAndFlush(pro);

            addListener(ch);
        } else {
            // 授权失败
            LOG.debug("Auth failure to Backstage: {}", back.getBackId());

            back.setSucceed(false);
            back.setErrorCode(ErrorCode.AUTH_FAILURE.name());
            pro.setBody(JsonUtils.toJson(back, Backstage.class).getBytes());
            ch.writeAndFlush(pro);

            // 关闭连接
            ch.close().syncUninterruptibly();
        }
    }

    private void addListener(final Channel ch) {
        sessionListenerManager.addListener(new SessionListener() {
            
        });
    }

}
