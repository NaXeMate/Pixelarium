package com.edu.mqt.pixelarium.model.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Status {

    @Enumerated(EnumType.STRING)
    private StatusType type;

    public enum StatusType {
        DRAFT("DF", "Draft"),
        PENDING("PD", "Pending"),
        SENT("ST", "Sent"),
        DELIVERED("DV", "Delivered");

        private String code;
        private String description;
        
        private StatusType(String code, String description) {
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

    public Status() {}

    public Status(StatusType type) {
        this.type = type;
    }

    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
    }
}
