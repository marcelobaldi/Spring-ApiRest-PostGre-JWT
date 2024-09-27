package io.github.marcelobaldi.ApiRestJwt.dto.exception;
import lombok.Data;
import java.util.List;

@Data
public class ErrorResponseDTO {
    private  String   message;
    private  Number   code;
    private  String   status;
    private  List<ErrorBeanValidationDTO> errorsList;

    public ErrorResponseDTO(String message, Number code, String status) {
        this.message = message;
        this.code    = code;
        this.status  = status;
    }

    public ErrorResponseDTO(String message, Number code, String status,
                            List<ErrorBeanValidationDTO> errorsList) {
        this.message    = message;
        this.code       = code;
        this.status     = status;
        this.errorsList = errorsList;
    }
}

