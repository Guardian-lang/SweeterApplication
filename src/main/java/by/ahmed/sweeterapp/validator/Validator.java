package by.ahmed.sweeterapp.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
