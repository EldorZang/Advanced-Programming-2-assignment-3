package com.example.testapp;

public class RegisterInput {
    private String id;
    private String nickName;
    private String password;

    public RegisterInput(String id, String nickname, String password) {
        this.id = id;
        this.nickName = nickname;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
