package firm.provider.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collector_firms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirmCollector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany
    private List<Order> orders;
}
