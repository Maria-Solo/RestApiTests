package com.mary.fixtures;


import com.mary.models.Client;

public class ClientFixture {
        public Client buildClient (String name, String email, String phone, String company) {
            Client client = new Client();
            client.setName(name);
            client.setEmail(email);
            client.setPhone(phone);
            client.setCompany(company);
            return client;
        }

        public Client invalidClient(){
            Client client = new Client();
           client.setName("");
           client.setEmail("");
           client.setPhone("");
           client.setCompany("");
           return client;
        }

        public Client validClient() {
            Client client = new Client();
            client.setName("Test");
            client.setEmail("test@test.com");
            client.setPhone("+1234561212");
            client.setCompany("SSL");
            return client;
        }
    }
