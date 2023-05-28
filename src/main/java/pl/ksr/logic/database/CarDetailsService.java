package pl.ksr.logic.database;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarDetailsService {

    private final CarDetailsRepository carDetailsRepository;

    public CarDetailsService(CarDetailsRepository carDetailsRepository) {
        this.carDetailsRepository = carDetailsRepository;
    }

    public List<CarDetails> getCarDetailsList() {
        return carDetailsRepository.findAll();
    }
}
