
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.airports.Airport;
import acme.entities.airports.AirportRepository;

@Validator
public class AirportValidator extends AbstractValidator<ValidAirport, Airport> {

	@Autowired
	private AirportRepository repository;


	@Override
	public boolean isValid(final Airport value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			Airport existingAirport = this.repository.findAirportByIata(value.getCode());
			boolean uniqueIata = existingAirport == null || existingAirport.getCode() == null || existingAirport.equals(value);

			super.state(context, uniqueIata, "code", "acme.validation.iata.duplicated.message");
		}

		return !super.hasErrors(context);
	}

}
