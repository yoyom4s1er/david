package firm.provider.model;

import firm.provider.util.LocationType;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
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
}
