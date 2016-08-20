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
import songm.sso.Constants;
import songm.sso.SSOException;
import songm.sso.SSOException.ErrorCode;
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
            throw new SSOException(ErrorCode.AUTH_DISABLED, "Auth failure");
        }
    }

}
