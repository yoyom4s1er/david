package firm.provider.model;

import firm.provider.utils.LocationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String producer;

    private float price;

    @ManyToMany
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    private long location_id;

    @Transient
    private Storage locationEntity;

    public Product(long id) {
        this.id = id;
    }
}
