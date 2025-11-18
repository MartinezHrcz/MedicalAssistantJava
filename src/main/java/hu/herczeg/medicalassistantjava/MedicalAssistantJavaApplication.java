package hu.herczeg.medicalassistantjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "hu.herczeg.medicalassistantjava.model")
public class MedicalAssistantJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalAssistantJavaApplication.class, args);
    }

}
