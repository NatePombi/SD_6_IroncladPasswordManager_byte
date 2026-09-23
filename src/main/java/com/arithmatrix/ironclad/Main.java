package com.arithmatrix.ironclad;

import com.arithmatrix.ironclad.crypto.EncryptionService;
import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storagev.CredentialStore;
import com.arithmatrix.ironclad.vaultStorage.VaultStorage;


import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        CredentialStore credentialStore = new CredentialStore();

        credentialStore.add(Credential.create("Github","demo","demo123"));
        credentialStore.add(Credential.create("Gmail","G-demo","demo123"));
        credentialStore.add(Credential.create("Twitter","T-demo","demo123"));

        String masterPassword = "demo-master-password";

        Path path = Path.of("vault.enc");

        VaultStorage vaultStorage = new VaultStorage();

        vaultStorage.save(path,credentialStore.getCredentials(),masterPassword);

        System.out.println("Vault Saved to: " + path.toAbsolutePath());

        List<Credential> credentials = vaultStorage.load(path,masterPassword);

        System.out.println("\n Loaded credentials\n");

        for(Credential credential : credentials){
            System.out.println(credential);
        }


    }
}