package app.practice.cafeapitask.global.exception;


import app.practice.cafeapitask.global.Object.GlobalResponse;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Valid Exception 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(GlobalResponse.error(errorMessage), HttpStatus.BAD_REQUEST);
    }

    // MethodArgumentTypeMismatchException 처리 UnexpectedTypeException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalResponse<?>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "잘못된 요청 파라미터 타입입니다: " + ex.getName() + " should be of type " + ex.getRequiredType().getName();
        return new ResponseEntity<>(GlobalResponse.error(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<GlobalResponse<?>> handleTypeMismatchException(UnexpectedTypeException ex) {
        return new ResponseEntity<>(GlobalResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GlobalResponse<?>> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(GlobalResponse.error(ex.getCode(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<?>> handleException(Exception ex) {
        return new ResponseEntity<>(GlobalResponse.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
