package pl.ksr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.ksr.database.CarDetailsRepository;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(CarDetailsRepository repository) {
        return (args) -> {
            repository.findAll().forEach(System.out::println);
        };
    }
}
