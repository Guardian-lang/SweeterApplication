package by.ahmed.sweeterapp.http.controller.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "by.ahmed.sweeterapp.http.controller.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
