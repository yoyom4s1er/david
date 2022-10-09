package firm.provider.dto;

import firm.provider.model.Order;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.List;

public class ProductDto {

    private long id;

    private String name;

    private String producer;

    private float price;

    private String locationName;
}
