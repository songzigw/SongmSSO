package songm.sso.operation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import songm.sso.operation.Operation;

@Component
public class SSOOperation {

    @Autowired
    private ApplicationContext context;

    private Map<Integer, Operation> ops = new HashMap<Integer, Operation>();

    @Bean(name = "operations")
    public Map<Integer, Operation> operations() {
        Map<String, Operation> beans = context.getBeansOfType(Operation.class);
        for (Operation op : beans.values()) {
            ops.put(op.operation(), op);
        }
        return ops;
    }

    public Operation find(Integer op) {
        return ops.get(op);
    }

}
