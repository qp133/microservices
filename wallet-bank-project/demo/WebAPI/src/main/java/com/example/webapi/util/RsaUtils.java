package com.example.webapi.util;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtils {

    public static PublicKey getPublicKey() {
        try {
            byte[] publicKeyBytes = readKeyFromFile("public.key");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read public key.");
        }
    }

    public static PrivateKey getPrivateKey() {
        try {
            byte[] privateKeyBytes = readKeyFromFile("private.key");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read private key.");
        }
    }

    private static byte[] readKeyFromFile(String fileName) throws IOException {
        Path keyPath = Paths.get(fileName);
        return Files.readAllBytes(keyPath);
    }

}
