package com.hyperativa.payment.securecard.port.services;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface CertificateWorkerPortServices {

	RSAPrivateKey getPrivateKeyFromString();

	RSAPublicKey getPublicKey() ;

}
