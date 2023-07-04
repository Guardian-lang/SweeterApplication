package by.ahmed.sweeterapp.validator;

import lombok.Getter;

import java.util.List;

public class ValidationException extends RuntimeException{
    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
