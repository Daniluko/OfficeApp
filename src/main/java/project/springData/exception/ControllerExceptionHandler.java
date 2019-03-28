package project.springData.exception;

import java.util.HashMap;
import java.util.Map;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;



import project.dto.ErrorMessage;

import javax.annotation.PostConstruct;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(ExceptionHandler.class);
    private static final Logger LOGMAIL = LogManager.getLogger("error-logger");

    private Map<String, String> validationCodeDescription = new HashMap<>();

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorMessage unexpected(Exception e) {
        LOGMAIL.error("Unexpected exception {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = { DeleteException.class, UpdateException.class })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody ErrorMessage entityExistingProblem(Exception e) {
        LOG.warn("Unprocessable entity {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage validationProblem(MethodArgumentNotValidException e) {
        LOGMAIL.warn("Request validation problem {}", e.getMessage());
        FieldError fe = e.getBindingResult().getFieldError();
        return new ErrorMessage(validationCodeDescription.get(fe.getDefaultMessage()));
    }

    @PostConstruct
    private void intValidationCodeDescription() {
        validationCodeDescription.put("1", " Office :too many or too few digits");
        validationCodeDescription.put("2", " Сity :must be not null");
        validationCodeDescription.put("3", " Region :must be not null");
        validationCodeDescription.put("4", " Target : too many or too few digital");
        validationCodeDescription.put("5", " Sales : must be not null");
        validationCodeDescription.put("6", " OfficeDetails : must be not null");
    }
}