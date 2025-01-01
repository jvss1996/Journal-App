package com.shekhawat.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    @Schema(description = "User's username")
    private String username;
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty
    @Schema(description = "User's password")
    private String password;
}
