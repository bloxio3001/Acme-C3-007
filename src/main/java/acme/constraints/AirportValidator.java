
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

		Airport existingAirportWithIata = this.repository.findAirportByIata(value.getCode());
		boolean uniqueIata = existingAirportWithIata == null || existingAirportWithIata.equals(value);

		super.state(context, uniqueIata, "code", "acme.validation.iata.duplicated.message");

		return !super.hasErrors(context);
	}

}
