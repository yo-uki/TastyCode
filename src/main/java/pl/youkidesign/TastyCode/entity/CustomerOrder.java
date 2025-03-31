package pl.youkidesign.TastyCode.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
@EntityListeners(AuditingEntityListener.class)
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "final_price", nullable = false)
    private double finalPrice;

    @ManyToOne
    @JoinColumn(name = "main_course")
    private Food foodOrdered;

    @ManyToOne
    @JoinColumn(name = "dessert")
    private Food dessertOrdered;

    @ManyToOne
    @JoinColumn(name = "drink")
    private Drink drinkOrdered;

    @Column(name = "special_requests")
    private String specialRequests;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime creationDate;

}
