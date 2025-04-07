package pl.youkidesign.TastyCode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.youkidesign.TastyCode.entity.Food;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantIdAndIsDessert(Long restaurantId, boolean isDessert);

    List<Food> findByIsDessert(boolean isDessert);

}
