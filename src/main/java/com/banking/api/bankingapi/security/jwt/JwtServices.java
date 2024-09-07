package com.banking.api.bankingapi.security.jwt;

import com.banking.api.bankingapi.modules.customer.Customer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServices {
    private RsaJsonWebKey rsaJsonWebKey;
    private JwtConsumer jwtConsumer;

    @PostConstruct
    public void ConfigJwtAndGenerateRSAKey() {
        try {
            this.rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            rsaJsonWebKey.setKeyId("k1");
            this.jwtConsumer = new JwtConsumerBuilder()
                    .setRequireSubject()
                    .setRequireExpirationTime()
                    .setVerificationKey(rsaJsonWebKey.getKey())
                    .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256)
                    .build();
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }
    }
    public JwtClaims getClaims(Customer customer) {
        JwtClaims jwtClaims = new JwtClaims();
        jwtClaims.setExpirationTimeMinutesInTheFuture(10);
        jwtClaims.setGeneratedJwtId();
        jwtClaims.setIssuedAtToNow();
        jwtClaims.setSubject(customer.getUsername());
        jwtClaims.setClaim("role", customer.getRole());
        return jwtClaims;
    }

    public String generateJwtToken(Customer customer) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(getClaims(customer).toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        return jws.getCompactSerialization();
    }

    public JwtClaims validateJwtToken(String token) {
        try {
            return  jwtConsumer.process(token).getJwtClaims();

        } catch (InvalidJwtException e) {
            if (e.hasExpired()) {
                throw new RuntimeException("Token has expired");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

}
