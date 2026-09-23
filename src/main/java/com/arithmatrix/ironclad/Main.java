package com.arithmatrix.ironclad;

import com.arithmatrix.ironclad.crypto.EncryptionService;
import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storage.CredentialStore;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws GeneralSecurityException {
        EncryptionService encryptionService = new EncryptionService();

        String masterPassword = "My-master-password";

        byte[] salt = encryptionService.generateSalt();
        byte[] iv = encryptionService.generateIV();

        SecretKey key = encryptionService.deriveKey(masterPassword,salt);

        String originalText = "Ironclad encryption test";

        byte[] encrypt = encryptionService.encrypt(originalText, key, iv);

        String decryptedText = encryptionService.decrypt(encrypt, key, iv);

        System.out.println("Original text: " + originalText);

        System.out.println("Encrypted text: " + encryptionService.encode(encrypt));

        System.out.println("Decrypted text: " + decryptedText);


        System.out.println("Successful: "+ originalText.equals(decryptedText));

    }
}