package az.orient.msshopcustomer.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExceptionResponse {
    private String message;
    private int code;
    private List<FieldException> fieldExceptions;
}
