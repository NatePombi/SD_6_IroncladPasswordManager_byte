package com.arithmatrix.ironclad;

import com.arithmatrix.ironclad.cli.UserInterface;
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
        UserInterface userInterface = new UserInterface();

        userInterface.menuInput();

    }
}