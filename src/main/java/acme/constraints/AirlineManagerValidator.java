
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.airlineManagers.AirlineManager;
import acme.entities.airlineManagers.AirlineManagerRepository;

@Validator
public class AirlineManagerValidator extends AbstractValidator<ValidAirlineManager, AirlineManager> {

	@Autowired
	private AirlineManagerRepository repository;


	@Override
	public boolean isValid(final AirlineManager value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			AirlineManager existingAirlineManager = this.repository.findManagerByIdentifierNumber(value.getIdentifierNumber());
			boolean uniqueIdNum = existingAirlineManager == null || existingAirlineManager.getIdentifierNumber() == null || existingAirlineManager.equals(value);

			super.state(context, uniqueIdNum, "identifierNumber", "acme.validation.airlinemanager.idnum.duplicated.message");
		}

		return !super.hasErrors(context);
	}

}
