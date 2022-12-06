package pl.eryk.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private boolean success;
    private String token;
    private String message;
}
