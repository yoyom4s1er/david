package firm.provider.model;

import firm.provider.util.LocationType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String producer;

    private float price;

    @ManyToMany
    private List<Order> orders;

    private LocationType locationType;

    private long location_id;
}