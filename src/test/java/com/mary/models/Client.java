package com.mary.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Client {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String company;
    public LocalDateTime createdAt;
}
