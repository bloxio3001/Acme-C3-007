
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.airlines.Airline;
import acme.entities.airlines.AirlineRepository;

@Validator
public class AirlineValidator extends AbstractValidator<ValidAirline, Airline> {

	@Autowired
	private AirlineRepository repository;


	@Override
	public boolean isValid(final Airline value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			Airline existingAirline = this.repository.findAirlineByIata(value.getCode());
			boolean uniqueIata = existingAirline == null || existingAirline.getCode() == null || existingAirline.equals(value);

			super.state(context, uniqueIata, "code", "acme.validation.iata.duplicated.message");
		}

		return !super.hasErrors(context);
	}

}
