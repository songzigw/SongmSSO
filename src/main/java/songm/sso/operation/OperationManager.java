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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import songm.sso.operation.Operation;

/**
 * 事件操作管理器
 * @author zhangsong
 *
 */
@Component
public class OperationManager {

    @Autowired
    private ApplicationContext context;

    private Map<Integer, Operation> ops = new HashMap<Integer, Operation>();

    @Bean(name = "operations")
    public Map<Integer, Operation> operations() {
        Map<String, Operation> beans = context.getBeansOfType(Operation.class);
        for (Operation op : beans.values()) {
            ops.put(op.handle(), op);
        }
        return ops;
    }

    public Operation find(Integer op) {
        return ops.get(op);
    }

}
