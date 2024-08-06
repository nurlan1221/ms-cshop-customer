package az.orient.msshopcustomer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity handleCustomerNotFoundException(CustomerNotFoundException ex) {
        var result = ExceptionResponse.builder().code(HttpStatus.NOT_FOUND.value()).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var fieldExceptions = ex.getFieldErrors().stream().map(f -> FieldException.builder().fieldName(f.getField()).errorMessage(f.getDefaultMessage()).build()).toList();
        var result = ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.toString())
                .fieldExceptions(fieldExceptions)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity handleInvalidPhoneNumberException(InvalidPhoneNumberException e){
        var result = ExceptionResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    }



}
