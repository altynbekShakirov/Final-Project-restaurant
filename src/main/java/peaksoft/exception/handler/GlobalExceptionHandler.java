package peaksoft.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(NoVacancyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleExistsException(NoVacancyException e) {
        return new ExceptionResponse(
                HttpStatus.CONFLICT,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }


    @ExceptionHandler(BadCredentialException.class)
    public ExceptionResponse handleBadCredentialException(BadCredentialException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse handleBadRequestException(BadRequestException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,
                e.getClass().getName(),
                e.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ExceptionResponse handleAlreadyExistException(AlreadyExistException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT,
                e.getClass().getName(),
                e.getMessage());
    }
}
