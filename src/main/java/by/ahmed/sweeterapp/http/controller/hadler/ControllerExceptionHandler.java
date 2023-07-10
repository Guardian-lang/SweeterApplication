package by.ahmed.sweeterapp.http.controller.hadler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice(basePackages = "by.ahmed.sweeterapp.http.controller")
public class ControllerExceptionHandler implements AccessDeniedHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("403 error");
        response.sendRedirect("/error/error403");
    }
}
