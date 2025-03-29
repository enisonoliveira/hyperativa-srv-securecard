package com.hyperativa.payment.securecard.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.hyperativa.payment.securecard.infrastructure.configuration.service.CertificateWorkerService;


@Configuration
public class JwtDecoderConfig {

   private final CertificateWorkerService certificateWorkerService;
  
   public JwtDecoderConfig(CertificateWorkerService certificateWorkerService){
    this.certificateWorkerService=certificateWorkerService;
}
    @Bean
    public JwtDecoder jwtDecoder()  {

        return NimbusJwtDecoder.withPublicKey(certificateWorkerService.getPublicKey()).build();
    }
}