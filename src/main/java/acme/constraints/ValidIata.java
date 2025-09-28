
package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})

@NotBlank
@Pattern(regexp = "^[A-Z]{3}$")

public @interface ValidIata {

	// Standard validation properties -----------------------------------------

	String message() default "{acme.validation.iata.message}";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
