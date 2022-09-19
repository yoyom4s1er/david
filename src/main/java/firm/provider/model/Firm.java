package firm.provider.model;

import firm.provider.util.FirmType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "firms")
@Data
public class Firm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private FirmType firmType;

    @OneToMany
    private List<Product> products;
}
