package com.mary.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
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
