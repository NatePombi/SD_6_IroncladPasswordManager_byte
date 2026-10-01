package com.arithmatrix.ironclad.cli;

import com.arithmatrix.ironclad.storagev.CredentialStore;
import com.arithmatrix.ironclad.vaultStorage.VaultStorage;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UserInterface {
    private VaultStorage vaultStorage = new VaultStorage();
    private CredentialStore credentialStore = new CredentialStore();
    private Path VAULT_PATH = Paths.get("vault.enc");

    public void menuInput(){


        if(Files.exists(VAULT_PATH)){
            System.out.println("Existing vault detected");
        }
        else {
            System.out.println("No Vault detected");
            System.out.println("A new vault will need to be created");
        }

        String masterpassword =readMasterPassword();

        System.out.println("Enter master password:" + masterpassword);


        boolean exit = true;

        Scanner scanner = new Scanner(System.in);


        while(exit){
            menu();

            System.out.print("Enter choice: ");
            String input = scanner.nextLine();

            switch (input){
                case "1": System.out.println("Adding Credentials");
                        break;
                case "2": System.out.println("Listing Credentials");
                        break;
                case  "3": System.out.println("Updating Credentials");
                        break;
                case "4": System.out.println("Deleting Credentials");
                        break;
                case  "5": System.out.println("Copying password");
                        break;
                case "6": exit=false;
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
}
