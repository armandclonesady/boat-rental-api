package com.java.tp.boat.rental;

import org.junit.jupiter.api.Test;

import com.java.tp.boat.rental.model.business.Client;

public class ClientTest {
    
    @Test
    public void testUpdateWithOverridesWhenValueIsPresent() {
        Client client1 = new Client(1L, "Simon", "Puech", "simon.puech@example.com", "0987654321", true);
        Client client2 = new Client(2L, "Atrioc", "Puech", "atrioc.puech@example.com", "1234567890", false);
        client1.updateWith(client2);
        assert(client1.getFirstName().equals("Atrioc"));
        assert(client1.getLastName().equals("Puech"));
        assert(client1.getEmail().equals("atrioc.puech@example.com"));
        assert(client1.getPhoneNumber().equals("1234567890"));
        assert(client1.getHasLicense().equals(false));
    }

    @Test
    public void testUpdateWithOverridesOnlyValuesThatArePresent() {
        Client client1 = new Client(1L, "Simon", "Puech", "simon.puech@example.com", "0987654321", true);
        Client client2 = new Client(2L, "Atrioc", null, null, "1234567890", null);
        client1.updateWith(client2);
        assert(client1.getFirstName().equals("Atrioc"));
        assert(client1.getLastName().equals("Puech"));
        assert(client1.getEmail().equals("simon.puech@example.com"));
        assert(client1.getPhoneNumber().equals("1234567890"));
        assert(client1.getHasLicense().equals(true));
    }
}
