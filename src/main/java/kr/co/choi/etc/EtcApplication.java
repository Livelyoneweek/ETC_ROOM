package kr.co.choi.etc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EtcApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtcApplication.class, args);
    }

}
