package pl.youkidesign.TastyCode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.youkidesign.TastyCode.entity.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
