package com.arithmatrix.ironclad.model;

public class Credential {
    private String service;
    private String username;
    private String password;


    public Credential(){}

    public static Credential create(String service, String username, String password) {
        Credential credential = new Credential();
        credential.service = service;
        credential.username = username;
        credential.password = password;

        return credential;
    }

    public String getService() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Credential [service=%s, username=%s, password=[Protected]]", service, username);
    }
}
