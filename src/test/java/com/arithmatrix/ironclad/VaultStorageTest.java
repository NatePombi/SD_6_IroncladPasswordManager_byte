package com.arithmatrix.ironclad;

import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storagev.CredentialStore;
import com.arithmatrix.ironclad.vaultStorage.VaultStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VaultStorageTest {

    @TempDir
    Path vaultPath;

    @Test
    void shouldSaveAndLoadCredentials() throws GeneralSecurityException, IOException {
        VaultStorage storage = new VaultStorage();

        List<Credential> credentials = List.of(Credential.create("Gmail","Nate","nate-password"));

        String masterPassword = "MasterPassword";

        Path path = vaultPath.resolve("vault.enc");

        storage.save(path,credentials,masterPassword);

        List<Credential> loaded = storage.load(path,masterPassword);

        assertNotNull(loaded);

        assertEquals(1, loaded.size());

        Credential credential = loaded.get(0);

        assertEquals("Gmail",credential.getService());
        assertEquals("Nate",credential.getUsername());
        assertEquals("nate-password",credential.getPassword());

    }

    @Test
    void shouldFail_LoadWithWrongMasterPassword() throws GeneralSecurityException, IOException {
        VaultStorage storage = new VaultStorage();

        List<Credential> credentials = List.of(Credential.create("Gmail","Nate","nate-password"));

        String masterPassword = "MasterPassword";

        Path path = vaultPath.resolve("vault.enc");

        storage.save(path,credentials,masterPassword);

        String wrongMasterPassword = "WrongMasterPassword";

        assertThrows(GeneralSecurityException.class,()->{
            storage.load(path,wrongMasterPassword);
        });

    }


    @Test
    void vaultFile_ShouldNotContainPlaintextPassword() throws GeneralSecurityException, IOException {
        VaultStorage storage = new VaultStorage();
        List<Credential> credentials = List.of(Credential.create("Gmail","Nate","nate-password"));

        String masterPassword = "MasterPassword";


        Path path = vaultPath.resolve("vault.enc");

        storage.save(path,credentials,masterPassword);

        String vaultContext = Files.readString(path);

        assertFalse(vaultContext.contains("nate-password"),"should not contain plaintext password");


    }
}
