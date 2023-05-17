package com.example.clieeeent.entity;

import lombok.Data;

@Data
public class UsersEntity {
    private int id;
    private String f_name;
    private String l_name;
    private String s_name;
    private int code_passport;
    private roleEntity role;

    public UsersEntity() {
    }

    public UsersEntity(int id, String f_name, String l_name, String s_name, int code_passport, roleEntity role) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.s_name = s_name;
        this.code_passport = code_passport;
        this.role = role;
    }

    // Геттеры и сеттеры для полей

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public int getCode_passport() {
        return code_passport;
    }

    public void setCode_passport(int code_passport) {
        this.code_passport = code_passport;
    }

    public roleEntity getRole() {
        return role;
    }

    public void setRole(roleEntity role) {
        this.role = role;
    }
}
