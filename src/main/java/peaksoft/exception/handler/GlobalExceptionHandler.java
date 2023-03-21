package peaksoft.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.ExceptionResponse;
import peaksoft.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException e){
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,e.getClass().getName(),e.getMessage()
        );
    }
    @ExceptionHandler(AlreadyExistException.class)

    public ExceptionResponse handleNotAlreadyExitException(NotFoundException e){
        return new ExceptionResponse(
                HttpStatus.CONFLICT,e.getClass().getName(),e.getMessage()
        );
    }
}
