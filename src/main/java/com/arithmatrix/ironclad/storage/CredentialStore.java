package com.arithmatrix.ironclad.storage;

import com.arithmatrix.ironclad.model.Credential;

import java.util.ArrayList;

public class CredentialStore {

    private final ArrayList<Credential> credentials = new ArrayList<>();


    public void add(Credential credential) {
        credentials.add(credential);
    }

    public ArrayList<Credential> getCredentials() {
        return credentials;
    }

    public boolean update(String service, String username, String password){
        for( Credential credential : credentials){
            if(credential.getService().equalsIgnoreCase(service)){
                credential.setUsername(username);
                credential.setPassword(password);
                return true;
            }
        }

        return false;
    }


    public boolean delete(String service){
        return credentials.removeIf(
                credential ->
                        credential.getService().equalsIgnoreCase(service)
        );
    }

}
