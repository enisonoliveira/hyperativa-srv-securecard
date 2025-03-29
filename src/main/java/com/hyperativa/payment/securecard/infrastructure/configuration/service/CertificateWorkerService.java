package com.hyperativa.payment.securecard.infrastructure.configuration.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface CertificateWorkerService {

	RSAPrivateKey getPrivateKeyFromString();

	RSAPublicKey getPublicKey() ;
	
}
