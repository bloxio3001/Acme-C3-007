
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.legs.Leg;
import acme.entities.legs.LegRepository;

@Validator
public class LegValidator extends AbstractValidator<ValidLeg, Leg> {

	@Autowired
	private LegRepository repository;


	@Override
	public boolean isValid(final Leg value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			// Check for unique flight number
			Leg existingLeg = this.repository.findLegByFlightNumber(value.getFlightNumber());
			boolean uniqueFlightNumber = existingLeg == null || existingLeg.getFlightNumber() == null || existingLeg.equals(value);

			super.state(context, uniqueFlightNumber, "flightNumber", "acme.validation.leg.flightnumber.duplicated.message");

			// Check for temporal coherence
			boolean temporalCoherence = value.getDeparture().before(value.getArrival());

			super.state(context, temporalCoherence, "arrival", "acme.validation.leg.temporal.message");

		}

		return !super.hasErrors(context);
	}

}
