package pl.youkidesign.TastyCode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.youkidesign.TastyCode.model.MenuItem;

@Data
@Entity
@Getter
@NoArgsConstructor
@Table(name = "food")
public class Food implements MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "is_dessert", nullable = false)
    private boolean isDessert;

}
