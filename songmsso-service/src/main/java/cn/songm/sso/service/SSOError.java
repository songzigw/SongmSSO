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
package cn.songm.sso.service;

import cn.songm.common.service.ErrorInfo;

public enum SSOError implements ErrorInfo {

    SSO_100("SSO_100");
    
    private final String errCode;
    
    @Override
    public String getErrCode() {
        return errCode;
    }

    private SSOError(String errCode) {
        this.errCode = errCode;
    }
    
    public SSOError getInstance(String errCode) {
        for (SSOError e : SSOError.values()) {
            if (e.getErrCode().equals(errCode)) {
                return e;
            }
        }
        throw new IllegalArgumentException("errCode");
    }
}
