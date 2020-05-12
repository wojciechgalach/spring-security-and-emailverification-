package pl.com.nur.springsecurityemailverification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.nur.springsecurityemailverification.model.Car;

import java.util.List;

@Repository
public interface CarDbRepo extends JpaRepository<Car, Long> {

    List<Car> findByYearProductionBetween(int min, int max);
}
