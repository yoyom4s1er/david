package firm.provider.handler;

import firm.provider.exception.WrongFieldsRegisterException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(WrongFieldsRegisterException.class)
    public ResponseEntity<ErrorMessage> wrongFieldsRegisterException(WrongFieldsRegisterException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), exception.getMessage()));
    }
}
