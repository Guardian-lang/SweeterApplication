package by.ahmed.sweeterapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class FirstAspect {

    @Pointcut("execution(public * by.ahmed.sweeterapp.http.controller.LoginController.validate(*))")
    public void isLogin() {
    }

    @Pointcut("execution(public * by.ahmed.sweeterapp.http.controller.RegistrationController.addUser(*))")
    public void isRegistration() {
    }

    @Before("isLogin()")
    public void userInLoginPage() {
        log.info("User on login page");
    }

    @AfterReturning(value = "isLogin() " +
            "&& target(service) ",
            returning = "result", argNames = "result, service")
    public void loginSuccess(Object result, Object service) {
        log.info("Login is success, result: {}", result);
    }

    @AfterThrowing(value = "isLogin()" +
            "&& target(service)",
    throwing = "ex")
    public void loginFail(Object service, Throwable ex) {
        log.info("Login failed by exception: {}", ex);
    }

    @Before("isRegistration()")
    public void userInRegistrationPage() {
        log.info("User on registration page");
    }

    @AfterReturning(value = "isRegistration() " +
            "&& target(service) ",
            returning = "result", argNames = "result, service")
    public void registrationSuccess(Object result, Object service) {
        log.info("Registration is success, result: {}", result);
    }

    @AfterThrowing(value = "isLogin()" +
            "&& target(service)",
            throwing = "ex")
    public void registrationFail(Object service, Throwable ex) {
        log.info("Registration failed by exception: {}", ex);
    }
}
