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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import songm.sso.SSOException;
import songm.sso.entity.Backstage;
import songm.sso.entity.Protocol;
import songm.sso.entity.User;
import songm.sso.service.SessionService;
import songm.sso.utils.JsonUtils;

/**
 * 用户报道操作
 * 
 * @author zhangsong
 *
 */
@Component("userEditHandler")
public class UserEditHandler extends AbstractHandler {

    private final Logger LOG = LoggerFactory.getLogger(UserEditHandler.class);

    @Autowired
    private SessionService sessionService;

    @Override
    public int operation() {
        return Operation.USER_EDIT.getValue();
    }

    @Override
    public void action(Channel ch, Protocol pro) throws SSOException {
        Backstage back = this.checkAuth(ch);

        User u = JsonUtils.fromJson(pro.getBody(), User.class);
        sessionService.editUser(u.getUserId(), u.getUserInfo());
        LOG.debug("UserEditHandler [BackId: {}, SesId: {}]", back.getBackId(), u.getSesId());

        pro.setBody(JsonUtils.toJson(u, User.class).getBytes());
        ch.writeAndFlush(pro);
    }

}
