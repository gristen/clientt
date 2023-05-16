package com.example.clieeeent.entity;

import lombok.Data;

@Data
public class UsersEntity {
    private Long id;
    private String f_name;
    private String l_name;
    private String s_name;
    private int code_passport;
    private String role;
}
