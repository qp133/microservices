package com.example.webapi.config;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

@Configuration
public class RsaKeyConfig {

    private static final String PRIVATE_KEY_FILE = "private.key";
    private static final String PUBLIC_KEY_FILE = "public.key";

    public RsaKeyConfig() {
        generateRandomKeys();
    }

    private void generateRandomKeys() {
        if (isPublicKeyExist()) {
            return;
        }
        if (isPrivateKeyExist()){
            return;
        }
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom();
            keyPairGenerator.initialize(2048, secureRandom);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            saveKeyToFile(privateKey, PRIVATE_KEY_FILE);
            saveKeyToFile(publicKey, PUBLIC_KEY_FILE);

            System.out.println("Random private key and public key generated and saved to files.");
            System.out.println("Private key is: " + privateKey);
            System.out.println("Public key is: " + publicKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate random keys.");
        }
    }

    private void saveKeyToFile(Key key, String fileName) throws IOException {
        byte[] keyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(keyBytes);
        fos.close();
    }

    private boolean isPublicKeyExist() {
        File publicKeyFile = new File(PUBLIC_KEY_FILE);
        return publicKeyFile.exists();
    }

    private boolean isPrivateKeyExist() {
        File privateKeyFile = new File(PRIVATE_KEY_FILE);
        return privateKeyFile.exists();
    }
}