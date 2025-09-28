
package acme.datatypes;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.Valid;

import acme.client.components.basis.AbstractDatatype;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.constraints.ValidReviewable;
import acme.entities.airlines.Airline;
import acme.entities.airports.Airport;
import acme.entities.reviews.ReviewableType;
import acme.entities.services.Service;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@ValidReviewable
public class Reviewable extends AbstractDatatype {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@Valid
	@Automapped
	private ReviewableType		reviewableType;

	@Optional
	@Valid
	@Automapped
	private Airport				airport;

	@Optional
	@Valid
	@Automapped
	private Airline				airline;

	@Optional
	@Valid
	@Automapped
	private Service				service;

	@Optional
	@Valid
	@Automapped
	private Serializable		flight; //Pending Flight implementation

}
