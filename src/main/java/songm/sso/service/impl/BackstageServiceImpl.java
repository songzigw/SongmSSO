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
package songm.sso.service.impl;

import org.springframework.stereotype.Component;

import songm.sso.Config;
import songm.sso.entity.Backstage;
import songm.sso.service.BackstageService;
import songm.sso.utils.CodeUtils;

@Component("backstageService")
public class BackstageServiceImpl implements BackstageService {

    private long MISTIMING = 3 * 1000;

    @Override
    public boolean auth(Backstage back) {
        long curr = back.getCreated().getTime();
        long mis = curr - back.getTimestamp();
        if (mis > MISTIMING || mis < -MISTIMING) {
            return false;
        }

        String key = Config.getInstance().getServerKey();
        String secret = Config.getInstance().getServerSecret();
        if (!key.equals(back.getServerKey())) {
            return false;
        }

        StringBuilder toSign = new StringBuilder(secret)
                .append(back.getNonce()).append(back.getTimestamp());
        String sign = CodeUtils.sha1(toSign.toString());
        if (!sign.equals(back.getSignature())) {
            return false;
        }

        return true;
    }

}
