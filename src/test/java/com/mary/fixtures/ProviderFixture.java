package com.mary.fixtures;

import com.mary.models.Provider;

public class ProviderFixture {
    public Provider buildProvider(String name, String email, String phone, Provider.ServiceType serviceType){
        Provider provider = new Provider();
        provider.setName(name);
        provider.setEmail(email);
        provider.setPhone(phone);
        provider.setServiceType(serviceType);
        return provider;
    }

    public Provider invalidProvider(){
        Provider provider = new Provider();
        provider.setName("");
        provider.setEmail("");
        provider.setPhone("");
        provider.setServiceType(Provider.ServiceType.SECURITY);
        return provider;
    }

    public Provider validProvider(){
        Provider provider = new Provider();
        provider.setName("Test provider");
        provider.setEmail("test@provider.com");
        provider.setPhone("+11234567788");
        provider.setServiceType(Provider.ServiceType.SECURITY);
        return provider;
    }


}
