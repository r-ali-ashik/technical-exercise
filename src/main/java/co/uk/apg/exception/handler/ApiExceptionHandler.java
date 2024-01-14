package co.uk.apg.exception.handler;

import co.uk.apg.dto.ErrorDto;
import co.uk.apg.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ErrorDto> handleApiExcpetion(ApiException ex, WebRequest webRequest) {

        if (ex.getHttpStatus().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            log.error("Something severe happened: {}", ex);
        }
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ErrorDto.builder()
                        .error(ex.getMessage())
                        .status(ex.getHttpStatus().value())
                        .build());
    }
}
