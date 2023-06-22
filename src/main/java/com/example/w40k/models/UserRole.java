package com.example.w40k.models;

public enum UserRole {
    IMPERIAL_NAVY("Imperial Navy"),
    SPACE_MARINES("Space Marines");

    public final String label;

    UserRole(String label) {
        this.label = label;
    }

    public static UserRole fromLabel(String label) {
        for (UserRole user : UserRole.values()) {
            if (user.label.equals(label)) {
                return user;
            }
        }
        return null;
    }
}