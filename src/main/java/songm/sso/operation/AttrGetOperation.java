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

import songm.sso.entity.Attribute;
import songm.sso.entity.Protocol;
import songm.sso.service.SessionService;
import songm.sso.utils.JsonUtils;

/**
 * Session属性操作
 * @author zhangsong
 *
 */
@Component("attrGetOperation")
public class AttrGetOperation extends AbstractOperation {

    private final Logger LOG = LoggerFactory.getLogger(AttrGetOperation.class);

    @Autowired
    private SessionService sessionService;

    @Override
    public int handle() {
        return Type.SESSION_ATTR_GET.getValue();
    }

    @Override
    public void action(Channel ch, Protocol pro) {
        super.action(ch, pro);

        Attribute attr = JsonUtils.fromJson(pro.getBody(), Attribute.class);
        String value = (String)sessionService.getAttribute(attr.getSesId(), attr.getKey());
        attr.setValue(value);
        LOG.debug("AttrGetOperation: {}", attr.getSesId());

        pro.setBody(JsonUtils.toJson(attr, Attribute.class).getBytes());
        ch.writeAndFlush(pro);
    }

}
