package com.hyperativa.payment.securecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.hyperativa.payment.securecard.model")  // Escaneia os modelos
@ComponentScan(basePackages = "com.hyperativa.payment.securecard")  // Escaneia todos os pacotes abaixo
public class SecurecardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurecardApplication.class, args);
    }
}
