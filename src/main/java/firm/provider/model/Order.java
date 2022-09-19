package firm.provider.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private FirmCollector firmCollector;

    @ManyToOne
    private Client client;

    private LocalDateTime date;

    @ManyToMany
    private List<Product> products;
}
