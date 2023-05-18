package pl.ksr;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ksr.model.CarDetails;

public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
}
