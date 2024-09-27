package io.github.marcelobaldi.ApiRestJwt.dto.exception;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ErrorBeanValidationDTO {
    private String   message;
    private String   field;
    private Optional parameter;
}

