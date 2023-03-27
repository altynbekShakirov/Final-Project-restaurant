package peaksoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class NoVacancyException extends Throwable {
    public NoVacancyException() {
        super();
    }

    public NoVacancyException(String message) {
        super(message);
    }
}
