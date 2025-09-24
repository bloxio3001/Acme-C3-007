
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.aircrafts.Aircraft;
import acme.entities.aircrafts.AircraftRepository;

@Validator
public class AircraftValidator extends AbstractValidator<ValidAircraft, Aircraft> {

	@Autowired
	private AircraftRepository repository;


	@Override
	public boolean isValid(final Aircraft value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			Aircraft existingAircraft = this.repository.findAircraftByRegistrationNumber(value.getRegistrationNumber());
			boolean uniqueIata = existingAircraft == null || existingAircraft.getRegistrationNumber() == null || existingAircraft.equals(value);

			super.state(context, uniqueIata, "code", "acme.validation.iata.duplicated.message");
		}

		return !super.hasErrors(context);
	}

}
