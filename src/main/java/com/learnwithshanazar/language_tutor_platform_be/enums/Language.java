package com.learnwithshanazar.language_tutor_platform_be.enums;

public enum Language {
    TURKISH("Turkish"),
    ENGLISH("English");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
