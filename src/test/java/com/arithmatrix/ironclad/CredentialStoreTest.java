package com.arithmatrix.ironclad;

import com.arithmatrix.ironclad.model.Credential;
import com.arithmatrix.ironclad.storagev.CredentialStore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialStoreTest {

    @Test
    void shouldAddAndRetrieveCredentials() {
        CredentialStore credentialStore = new CredentialStore();

        credentialStore.add(Credential.create("Gmail","nate","nate-password"));

        List<Credential> credentials = credentialStore.getCredentials();

        assertNotNull( credentials);

        assertEquals(1,credentials.size());

        Credential credential = credentials.get(0);

        assertEquals("Gmail",credential.getService());
        assertEquals("nate",credential.getUsername());
        assertEquals("nate-password",credential.getPassword());

    }

    @Test
    void shouldUpdateAndRetrieveCredentials() {
        CredentialStore credentialStore = new CredentialStore();

        credentialStore.add(Credential.create("Gmail","nate","nate-password"));
        credentialStore.add(Credential.create("Github","nate","nate-password"));


        credentialStore.update("Github","Kev","kev-password");

        List<Credential> credentials = credentialStore.getCredentials();

        assertNotNull(credentials);

        assertEquals(2,credentials.size());

        Credential credential = credentials.get(0);
        Credential credential2 = credentials.get(1);

        assertEquals("Gmail",credential.getService());
        assertEquals("nate",credential.getUsername());
        assertEquals("nate-password",credential.getPassword());

        assertEquals("Github",credential2.getService());
        assertEquals("Kev",credential2.getUsername());
        assertEquals("kev-password",credential2.getPassword());
    }


    @Test
    void shouldDeleteAndRetrieveCredentials() {
        CredentialStore credentialStore = new CredentialStore();

        credentialStore.add(Credential.create("Gmail","nate","nate-password"));
        credentialStore.add(Credential.create("Github","nate","nate-password"));

        credentialStore.delete("Github");

        List<Credential> credentials = credentialStore.getCredentials();

        assertNotNull(credentials);

        assertEquals(1,credentials.size());

        Credential credential = credentials.get(0);

        assertEquals("Gmail",credential.getService());
        assertEquals("nate",credential.getUsername());
        assertEquals("nate-password",credential.getPassword());
    }

}
