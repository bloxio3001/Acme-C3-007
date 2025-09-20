
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;

@Validator
public class IataValidator extends AbstractValidator<ValidIata, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		assert context != null;

		boolean correctMatch = value.matches("[A-Z]{3}");
		super.state(context, correctMatch, "value", "acme.validation.iata.wrong-format.message");

		return !super.hasErrors(context);
	}

}
