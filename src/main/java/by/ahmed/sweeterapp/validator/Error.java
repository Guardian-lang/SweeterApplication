package by.ahmed.sweeterapp.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    final static String CODE_MESSAGE = "invalid.";
    String code;
    String message;

    public static String getMessage(String message) {
        return CODE_MESSAGE + message;
    }
}
