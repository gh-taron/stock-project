package  com.taron.stocks.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products", schema = "stock_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "id_enterprise", nullable = false)
    private Integer idEnterprise;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
