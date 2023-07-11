package by.ahmed.sweeterapp.annotation;

import by.ahmed.sweeterapp.SweeterAppApplication;
import by.ahmed.sweeterapp.SweeterAppApplicationTests;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {SweeterAppApplicationTests.class, SweeterAppApplication.class})
@Transactional
public @interface IntegrationTesting {
}