package firm.provider.model;

import firm.provider.util.OperationType;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
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

    private OperationType operationType;

    private long OperationTargetId;

    private LocalDateTime date;

    @ManyToMany
    private List<Product> products;

}
