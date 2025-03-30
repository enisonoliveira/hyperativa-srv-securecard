package com.hyperativa.payment.securecard.port;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface CertificateWorkerPort {

	RSAPrivateKey getPrivateKeyFromString();

	RSAPublicKey getPublicKey() ;
	
}
