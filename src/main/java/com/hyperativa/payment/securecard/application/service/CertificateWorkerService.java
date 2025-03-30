package com.hyperativa.payment.securecard.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import io.micrometer.core.instrument.util.IOUtils;

import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyperativa.payment.securecard.port.services.CertificateWorkerPortServices;

@Service
public class CertificateWorkerService implements CertificateWorkerPortServices {

    @Value("${application.public.key.path}")
    private String pathPublic;

    @Value("${application.private.key.path}")
    private String pathPrivate;

    private static final String ALGORITHM = "RSA";

    private static final Logger log = LoggerFactory.getLogger(CertificateWorkerService.class);

    /**
     * Retrieve the Private Key from an X.509 PEM file.
     *
     * @return Private Key
     */
    @Override
    public RSAPrivateKey getPrivateKeyFromString() {
        try {
            String pemFile = verifyPath(this.pathPrivate);

            byte[] keyBytes = parseDERFromPEM(pemFile, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE-----");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
            return (RSAPrivateKey) factory.generatePrivate(spec);

        }  catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("Error retrieving private key: {}", e.getMessage(), e);
		}
		
        return null;
    }

    /**
     * Retrieve the Public Key from an X.509 PEM file.
     *
     * @return Public Key
     */
	@Override
    public RSAPublicKey getPublicKey(){
        try {
            String pemFile = verifyPath(this.pathPublic);

            byte[] encoded = parseDERFromPEM(pemFile, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC-----");
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("Error retrieving private key: {}", e.getMessage(), e);
		}
		
        return null;
    }

    /**
     * Retrieve the certificate from an X.509 PEM file.
     *
     * @param path File path for the PEM file
     * @return Certificate Key as String
     */
    private String verifyPath(String path) throws IOException {
        InputStream resource = new ClassPathResource(path).getInputStream();
        String key = IOUtils.toString(resource, StandardCharsets.UTF_8);

        if (key.isEmpty()) {
            log.error("File not found!");
        }

        return key;
    }

    /**
     * Helper method to extract Base64-encoded DER data from a PEM key.
     *
     * @param data           The full PEM file as String
     * @param beginDelimiter The PEM header delimiter
     * @param endDelimiter   The PEM footer delimiter
     * @return Decoded key bytes
     */
    protected static byte[] parseDERFromPEM(String data, String beginDelimiter, String endDelimiter) {
        String[] tokens = data.split(beginDelimiter);
        tokens = tokens[1].split(endDelimiter);
        return DatatypeConverter.parseBase64Binary(tokens[0]);
    }
}