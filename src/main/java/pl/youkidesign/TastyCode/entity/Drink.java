package pl.youkidesign.TastyCode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.youkidesign.TastyCode.model.MenuItem;

@Data
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
 public class Drink implements MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "price", nullable = false)
    double price;

    @Column(name = "contains_alco", nullable = false)
    boolean containsAlco;

}
