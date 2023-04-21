package hr.algebra.surfsafly.exception;

import hr.algebra.surfsafly.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.error("", e.getMessage()));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ApiResponseDto> handlePasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.error("", e.getMessage()));
    }

/*    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleOtherExceptions(Exception e) {

        if (e instanceof IllegalStateException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.error("", e.getCause().getMessage()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDto.error("", e.getMessage()*//*"An unexpected error occurred"*//*));
        }
    }*/
}
