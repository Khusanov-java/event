package org.example.event.dtos.req;

import lombok.Data;

@Data
public class VerificationDTO {
    private String userId;
    private String code;
}
