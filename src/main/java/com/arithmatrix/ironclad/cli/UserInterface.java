package com.arithmatrix.ironclad.cli;

import java.util.Scanner;

public class UserInterface {

    public void menuInput(){
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
}
