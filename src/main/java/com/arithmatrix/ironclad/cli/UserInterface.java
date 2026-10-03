package com.arithmatrix.ironclad.cli;

import com.arithmatrix.ironclad.clipboard.ClipboardService;
import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storagev.CredentialStore;
import com.arithmatrix.ironclad.vaultStorage.VaultStorage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private VaultStorage vaultStorage = new VaultStorage();
    private CredentialStore credentialStore = new CredentialStore();
    private Path VAULT_PATH = Paths.get("vault.enc");
    private InterfaceMethods methods;
    private ClipboardService clipboardService = new ClipboardService();

    public void menuInput(){
        Scanner scanner = new Scanner(System.in);
        methods = new InterfaceMethods(scanner);

        String masterPassword = "";

        if(Files.exists(VAULT_PATH)){
            System.out.println("Existing vault detected");

             masterPassword = readMasterPassword();

            boolean unlock = unlockVault(vaultStorage,credentialStore,masterPassword);

            if(!unlock){
                System.out.println("Unable to access vault.");
                return;
            }
        }
        else {
            System.out.println("No Vault detected");
            System.out.println("A new vault will need to be created");

             masterPassword = createMasterPassword();

            createVault(vaultStorage,credentialStore,masterPassword);
        }


        boolean exit = true;



        while(exit){
            menu();

            System.out.print("Enter choice: ");
            String input = scanner.nextLine();

            switch (input){
                case "1": methods.addCredential(credentialStore,vaultStorage, masterPassword);
                        break;
                case "2": methods.listCredentials(credentialStore);
                        break;
                case  "3": methods.updateCredential(credentialStore,vaultStorage,masterPassword);
                        break;
                case "4": methods.deleteCredential(credentialStore,vaultStorage,masterPassword);
                        break;
                case  "5": methods.copyPassword(credentialStore,clipboardService);
                        break;
                case "6": exit=false;
                            clipboardService.shutDown();
                           System.out.println("GoodBye!");
                        break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void menu(){
        System.out.println("******************************");
        System.out.println("\tIRONCLAD PASSWORD MANAGER");
        System.out.println("******************************");
        System.out.println("1. Add Credential");
        System.out.println("2. List Credentials");
        System.out.println("3. Update Credential");
        System.out.println("4. Delete Credential");
        System.out.println("5. Copy password");
        System.out.println("6. Exit");
        System.out.println("******************************\n");

    }

    private String readMasterPassword(){
        java.io.Console console = System.console();

        if(console == null){
            throw new IllegalStateException(
                    "Secure password input unavailable. " +
                    "Please run Ironclad from a real terminal"
            );

        }

        char[] password = console.readPassword("Password: ");

        return new String(password);
    }

    private String createMasterPassword(){

        while(true){
            String password = readMasterPassword();

            if(password.isBlank()){
                System.out.println("Master password cannot be empty");
                continue;

            }

            String confirmation = readMasterPasswordConfirmation();

            if(!password.equals(confirmation)){
                System.out.println("Passwords do not match, please try again.");
                continue;
            }

            return password;
        }
    }

    private String readMasterPasswordConfirmation(){
         java.io.Console console = System.console();

         if(console == null){
             throw new IllegalStateException(
                     "Secure password input unavailable. " +
                             "Please run Ironclad from a real terminal"
             );
         }

         char[] password = console.readPassword("Confirm master password: ");

         return new String(password);
    }

    private void createVault(VaultStorage vaultStorage, CredentialStore credentialStore, String masterPassword){
        try{
            vaultStorage.save(VAULT_PATH,credentialStore.getCredentials(),masterPassword);
            System.out.println("Successfully created vault");
        }
        catch (Exception e){
            System.out.println("Unable to create vault");
        }
    }

    private boolean unlockVault(VaultStorage vaultStorage, CredentialStore credentialStore, String masterPassword){

        try{
            List<Credential> credentials = vaultStorage.load(VAULT_PATH,masterPassword);

            for(Credential credential : credentials){
                credentialStore.add(credential);
            }


            System.out.println("Vault unlocked Successfully");


            return true;

        }

        catch (Exception e){
            System.out.println("Unable to unlock vault. "
            + "Please check your master password.");
            return false;

        }

    }

}
