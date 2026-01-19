package com.edu.mqt.pixelarium.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Status {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

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

    public Status(StatusType status) {
        this.status = status;
    }

    public StatusType getType() {
        return status;
    }

    public void setType(StatusType status) {
        this.status = status;
    }
}
