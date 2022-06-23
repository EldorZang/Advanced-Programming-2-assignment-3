package com.example.myapplication5;

public class AndroidToken {
    private String id;
    private String firebaseToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public AndroidToken(String id, String firebaseToken) {
        this.id = id;
        this.firebaseToken = firebaseToken;
    }
}
