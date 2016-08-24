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
import songm.sso.entity.Protocol;

public interface Operation {

    public int operation();

    void action(Channel ch, Protocol pro);

    public static enum Type {
        /** 连接授权 */
        CONN_AUTH(1),
        
        /** 用户报道 */
        USER_REPORT(4),
        
        /** Session */
        SESSION_CREATE(5),
        SESSION_UPDATE(6),
        SESSION_REMOVE(7);
        
        private final int value;
        
        private Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        
        public static Type getInstance(int v) {
            for (Type type : Type.values()) {
                if (type.getValue() == v) {
                    return type;
                }
            }
            return null;
        }
    }
}
