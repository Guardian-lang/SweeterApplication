package by.ahmed.sweeterapp.validator;

public interface Validator<T> {
    boolean isValid(T object);
}
