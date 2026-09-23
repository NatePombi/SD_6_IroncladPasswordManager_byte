package com.arithmatrix.ironclad.vaultStorage;

import com.arithmatrix.ironclad.crypto.EncryptionService;
import com.arithmatrix.ironclad.model.Credential;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.List;

public class VaultStorage {
    private final EncryptionService encryptionService;
    private final ObjectMapper objectMapper;

    public VaultStorage() {
        this.encryptionService = new  EncryptionService();
        this.objectMapper = new ObjectMapper();
    }

    public void save(Path vaultPath,
                     List<Credential> credentials,
                     String masterPassword) throws GeneralSecurityException, IOException {
        byte[] salt = encryptionService.generateSalt();
        byte[] iv = encryptionService.generateIV();

        SecretKey key = encryptionService.deriveKey(masterPassword, salt);

        String json = objectMapper.writeValueAsString(credentials);

        byte[] encrypted = encryptionService.encrypt(json,key,iv);

        VaultData vaultData = VaultData.createVaultData(1, encryptionService.encode(salt),encryptionService.encode(iv), encryptionService.encode(encrypted));

        String vaultJson = objectMapper.writeValueAsString(vaultData);

        Files.writeString(vaultPath,vaultJson);
    }


    public List<Credential> load(Path vaultPath,String masterPassword) throws GeneralSecurityException, IOException {
        String vaultJson = Files.readString(vaultPath);

        VaultData vaultData = objectMapper.readValue(vaultJson,VaultData.class);

        byte[] salt = encryptionService.decode(vaultData.getSalt());

        byte[] iv = encryptionService.decode(vaultData.getIv());

        byte[] encrypted = encryptionService.decode(vaultData.getData());

        SecretKey key = encryptionService.deriveKey(masterPassword, salt);

        String json = encryptionService.decrypt(encrypted,key,iv);

        return objectMapper.readValue(json, new TypeReference<List<Credential>>() {});
    }
}
