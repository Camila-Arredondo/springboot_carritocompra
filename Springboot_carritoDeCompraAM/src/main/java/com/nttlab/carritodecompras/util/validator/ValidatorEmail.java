package com.nttlab.carritodecompras.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorEmail {
	
	String message() default "{emailValido.mensajeEmailInvalido}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}