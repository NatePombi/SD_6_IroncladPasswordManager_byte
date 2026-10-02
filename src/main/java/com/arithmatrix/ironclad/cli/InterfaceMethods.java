package com.arithmatrix.ironclad.cli;

import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storagev.CredentialStore;

import java.util.Scanner;

public class InterfaceMethods {
    private Scanner scanner;
    public InterfaceMethods(Scanner scanner){
        this.scanner = scanner;
    }


    public void addCredential(CredentialStore credentialStore){

        System.out.println();
        System.out.println("*** Add Credential ***");

        System.out.println("Service: ");
        String service = scanner.nextLine().trim();

        if(service.isBlank()){
            System.out.println("Service cannot be empty");
            return;
        }

        System.out.println("Username: ");
        String username = scanner.nextLine().trim();

        if(username.isBlank()){
            System.out.println("Username cannot be empty");
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

        System.out.println("Credential added successfully. ");

    }
}
