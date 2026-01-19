package com.edu.mqt.pixelarium.model.enumerated;

public enum Category {
    APPLE("APPL", "Apple"),
    NINTENDO_SWITCH("NSW", "Nintendo Switch"),
    NINTENDO_SWITCH_2("NSW2", "Nintendo Switch 2"),
    PC("PC", "PC"),
    ACCESSORIES("ACSS", "Accessories");

    private String code;
    private String description;

    private Category(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}