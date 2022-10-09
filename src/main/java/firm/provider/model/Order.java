package firm.provider.model;

import firm.provider.utils.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Firm firm;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private long OperationTargetId;

    private LocalDateTime date;

    private float totalPrice;

    @ManyToMany
    private List<Product> products;

    public Order(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", firm=" + firm +
                ", operationType=" + operationType +
                ", OperationTargetId=" + OperationTargetId +
                ", date=" + date +
                ", products=" + products +
                '}';
    }
}
