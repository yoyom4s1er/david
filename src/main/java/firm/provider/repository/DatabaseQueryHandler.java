package firm.provider.repository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseQueryHandler {

    private final Map<String, OperationHandler> handlers = new HashMap<>();

    @Autowired
    public DatabaseQueryHandler(List<OperationHandler> handlers) {
        for (OperationHandler handler : handlers) {
            this.handlers.put(handler.getName(), handler);
        }
    }


}
