package com.javacode.jwt_security.model;

public enum Role {
    USER("USER"), ADMIN("ADMIN"), MODERATOR("MODERATOR");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    String getLabel() {
        return label;
    }
}

