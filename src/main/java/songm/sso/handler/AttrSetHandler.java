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

import songm.sso.SSOException;
import songm.sso.entity.Attribute;
import songm.sso.entity.Backstage;
import songm.sso.entity.Protocol;
import songm.sso.entity.Result;
import songm.sso.service.SessionService;
import songm.sso.utils.JsonUtils;

/**
 * Session属性操作
 * 
 * @author zhangsong
 *
 */
@Component("attrSetHandler")
public class AttrSetHandler extends AbstractHandler {

    private final Logger LOG = LoggerFactory.getLogger(AttrSetHandler.class);

    @Autowired
    private SessionService sessionService;

    @Override
    public int operation() {
        return Operation.SESSION_ATTR_SET.getValue();
    }

    @Override
    public void action(Channel ch, Protocol pro) throws SSOException {
        Backstage back = this.checkAuth(ch);

        Attribute attr = JsonUtils.fromJson(pro.getBody(), Attribute.class);
        sessionService.setAttribute(attr.getSesId(), attr.getKey(), attr.getValue());
        LOG.debug("AttrSetHandler [BackId: {}, SesId: {}]", back.getBackId(), attr.getSesId());

        Result<Backstage> res = new Result<Backstage>();
        res.setData(back);
        pro.setBody(JsonUtils.toJsonBytes(res, res.getClass()));
        ch.writeAndFlush(pro);
    }

}
