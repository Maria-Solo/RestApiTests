package com.apiTests;


import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mappers {
    public ObjectMapper getObjectMApper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Module() {
            @Override
            public String getModuleName() {
                return "";
            }

            @Override
            public Version version() {
                return null;
            }

            @Override
            public void setupModule(SetupContext setupContext) {

            }
        });
        return objectMapper;
    }
}
