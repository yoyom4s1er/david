package firm.provider.dto;

import firm.provider.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class BuyProduct {
    private long operationTargetId;
    private String operationType;
    private List<Long> products;

    @Override
    public String toString() {
        return "BuyProduct{" +
                "operationTargetId=" + operationTargetId +
                ", operationType='" + operationType + '\'' +
                ", productsId=" + products +
                '}';
    }
}
