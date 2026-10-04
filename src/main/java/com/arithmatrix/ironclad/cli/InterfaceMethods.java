package com.arithmatrix.ironclad.cli;

import com.arithmatrix.ironclad.clipboard.ClipboardService;
import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storagev.CredentialStore;
import com.arithmatrix.ironclad.vaultStorage.VaultStorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class InterfaceMethods {
    private Scanner scanner;
    private Path VAULT_PATH = Paths.get("vault.enc");
    public InterfaceMethods(Scanner scanner){
        this.scanner = scanner;
    }


    public void addCredential(CredentialStore credentialStore, VaultStorage storage, String masterPassword){

        System.out.println();
        System.out.println("*** Add Credential ***");

        System.out.println("Service: ");
        String service = scanner.nextLine().trim();

        if(service.isBlank()){
            System.out.println("Service cannot be empty");
            return;
        }

        if(exists(credentialStore,service)){
            System.out.println("A credential for this service already exists");
            return;
        }

        System.out.println("Username: ");
        String username = scanner.nextLine().trim();

        if(username.isBlank()){
            System.out.println("Username cannot be empty");
            return;
        }

        java.io.Console console = System.console();

        if(console == null){
            System.out.println("Secure password input is unavailable. " +
                    "Please run Ironclad from a real terminal.");

            return;
        }

        char[] passwordChars = console.readPassword("Password: ");

        String password = new String(passwordChars);

        if(password.isBlank()){
            System.out.println("Password cannot be empty");
            return;
        }


        Credential credential = Credential.create(service,username,password);

        credentialStore.add(credential);

        try {

            storage.save(VAULT_PATH,credentialStore.getCredentials(),masterPassword);

            System.out.println("Credential added successfully. ");
        }
        catch (Exception e){
            credentialStore.delete(service);
            System.out.println("Unable to save credential to vault.");

            System.out.println("The credential was not added");
        }

    }

    public void listCredentials(CredentialStore credentialStore){
        System.out.println();
        System.out.println("*** Saved Credentials ***");

        List<Credential> credentials = credentialStore.getCredentials();

        if(credentials.isEmpty()){
            System.out.println("No Credentials saved");
        }

        for(int i = 0; i< credentials.size();i++){
            Credential credential  = credentials.get(i);

            System.out.println((i + 1) + ". " + credential.getService());
            System.out.println(
                    "\tUsername: " + credential.getUsername());


            System.out.println(
                    "\tPassword: [Protected]"
            );
        }
    }

    public void updateCredential(CredentialStore credentialStore,VaultStorage storage, String masterPassword){
        System.out.println();
        System.out.println("*** Update Credential ***");


        System.out.println("Service: ");
        String service = scanner.nextLine().trim();

        if(service.isBlank()){
            System.out.println("Service cannot be empty");
            return;
        }


        System.out.println("New Username: ");
        String username = scanner.nextLine().trim();

        if(username.isBlank()){
            System.out.println("Username cannot be empty");
            return;
        }


        java.io.Console console = System.console();

        if(console == null){
            System.out.println(
                    "Secure password input is unavailable. " +
                    "Please run Ironclad from a real terminal."
            );
            return;
        }

        char[] passwordChars = console.readPassword("New Password: ");

        String password = new String(passwordChars);


        if(password.isBlank()){
            System.out.println("Password cannot be empty");
            return;
        }


        boolean updated = credentialStore.update(service,username,password);

        if(!updated){
            System.out.println("No credentials found for service: " + service);
            return;
        }


        try{
            storage.save(VAULT_PATH,credentialStore.getCredentials(),masterPassword);

            System.out.println("Successfully updated credential");
        }

        catch (Exception e){
            System.out.println("Credential was updated in memory. " +
                    "but could not be saved to the vault.");
        }


    }


    public void deleteCredential(CredentialStore credentialStore, VaultStorage vaultStorage, String masterPassword){
        System.out.println();
        System.out.println("*** Delete Credential ***");

        System.out.println("Service: ");
        String service = scanner.nextLine().trim();

        if(service.isBlank()){
            System.out.println("Service cannot be empty");
            return;
        }

        Credential credentialToDelete = null;

        for(Credential credential : credentialStore.getCredentials()){
            if (credential.getService().equals(service)){
                credentialToDelete = credential;
                break;
            }
        }

        if( credentialToDelete == null){
            System.out.println("No Credential found for service: " + service);
        }

        boolean deleted = credentialStore.delete(service);

        if(!deleted){
            System.out.println("Unable to delete credential");
            return;
        }

        try{
            vaultStorage.save(VAULT_PATH, credentialStore.getCredentials(),masterPassword);

            System.out.println("Successfully deleted credential");
        }

        catch (Exception e){
            credentialStore.add(credentialToDelete);

            System.out.println("Unable to the updated vault.");

            System.out.println("The Credential was not deleted");
        }

    }

    public void copyPassword(CredentialStore credentialStore, ClipboardService clipboardService){
        System.out.println();
        System.out.println("*** Copy Password ***");

        System.out.println("Service: ");
        String service = scanner.nextLine().trim();

        if(service.isBlank()){
            System.out.println("Service cannot be empty");
        }

        for(Credential credential: credentialStore.getCredentials()){
            if(credential.getService().equals(service)){
                clipboardService.copy(credential.getPassword());

                System.out.println("Password copied to clipboard");

                System.out.println("Clipboard will clear in 15 seconds");

                return;
            }
        }

        System.out.println("No credentials found for service: " + service);
    }


    private boolean exists(CredentialStore credentialStore, String service){

        for(Credential credential : credentialStore.getCredentials()){
            if(service.equals(credential.getService())){
                return true;
            }
        }

        return false;
    }
}
