package firm.provider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongFieldsRegisterException extends RuntimeException{

    public WrongFieldsRegisterException(String message) {
        super(message);
    }
}
