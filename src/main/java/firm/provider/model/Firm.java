package firm.provider.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "firms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Firm implements Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    @OneToMany
    private List<Order> orders;

    @Transient
    private List<Product> products;

    public Firm(long id) {
        this.id = id;
    }

    public Firm(String name) {
        this.name = name;
    }
}
