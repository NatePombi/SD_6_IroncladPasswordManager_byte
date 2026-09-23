package com.arithmatrix.ironclad;

import com.arithmatrix.ironclad.crypto.EncryptionService;
import org.junit.jupiter.api.Test;

import javax.crypto.AEADBadTagException;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionServiceTest {

    @Test
    void shouldEncryptAndDecrypt_ReturnOriginalText() throws GeneralSecurityException {
        EncryptionService encryptionService = new EncryptionService();

        String originalText = "Tester-Text";
        String masterPassword = "Tester-Password";

        byte[] salt = encryptionService.generateSalt();
        byte[] iv = encryptionService.generateIV();

        SecretKey key = encryptionService.deriveKey(masterPassword,salt);

        byte[] encrypt = encryptionService.encrypt(originalText,key,iv);

        String decrypt = encryptionService.decrypt(encrypt,key,iv);

        assertFalse(decrypt.isBlank());

        assertEquals(originalText,decrypt);
    }

    @Test
    void shouldFailDecrypt_WrongKey() throws GeneralSecurityException {
        EncryptionService encryptionService = new EncryptionService();

        String originalText = "Tester-Text";
        String masterPassword = "Tester-Password";

        byte[] salt = encryptionService.generateSalt();
        byte[] iv = encryptionService.generateIV();

        SecretKey key = encryptionService.deriveKey(masterPassword,salt);

        byte[] encrypt = encryptionService.encrypt(originalText,key,iv);

        SecretKey wrongKey = encryptionService.deriveKey("wrong-password",salt);

        assertThrows(AEADBadTagException.class,()->
                encryptionService.decrypt(encrypt,wrongKey,iv)
        );


    }


    @Test
    void differentIv_shouldProduceDifferentText_Fail() throws GeneralSecurityException {
        EncryptionService encryptionService = new EncryptionService();

        String originalText = "Tester-Text";
        String masterPassword = "Tester-Password";

        byte[] salt = encryptionService.generateSalt();
        byte[] iv = encryptionService.generateIV();

        SecretKey key = encryptionService.deriveKey(masterPassword,salt);

        byte[] encrypt = encryptionService.encrypt(originalText,key,iv);

        byte[] differentIV = encryptionService.generateIV();

        assertThrows(AEADBadTagException.class,()->
                encryptionService.decrypt(encrypt,key,differentIV)) ;
    }
}
