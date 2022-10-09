package firm.provider.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany
    private List<Order> orders;

    @Transient
    private List<Product> products;
}
