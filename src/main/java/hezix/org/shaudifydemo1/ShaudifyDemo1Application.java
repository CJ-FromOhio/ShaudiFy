package hezix.org.shaudifydemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShaudifyDemo1Application {
    public static void main(String[] args) {
        SpringApplication.run(ShaudifyDemo1Application.class, args);
    }
}
