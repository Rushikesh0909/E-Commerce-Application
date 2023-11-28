package com.bikkadIt.exception;

import com.bikkadIt.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourseNotFoundException ex){

        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).status(false).httpStatus(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String, Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });

return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ApiResponse> badApiRequestHandler(BadRequest br){

        ApiResponse apiResponse = ApiResponse.builder().message(br.getMessage()).status(false).httpStatus(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}
