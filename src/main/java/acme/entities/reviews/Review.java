
package acme.entities.reviews;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidLongText;
import acme.constraints.ValidReview;
import acme.constraints.ValidShortText;
import acme.entities.airlines.Airline;
import acme.entities.airports.Airport;
import acme.entities.flights.Flight;
import acme.entities.services.Service;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidReview
public class Review extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidShortText
	@Automapped
	private String				author;

	@Mandatory
	@ValidMoment(past = true)
	@Automapped
	private Date				postMoment;

	@Mandatory
	@ValidShortText
	@Automapped
	private String				subject;

	@Mandatory
	@ValidLongText
	@Automapped
	private String				body;

	@Mandatory
	@Valid
	@Automapped
	private ReviewableType		reviewableType;

	@Optional
	@Valid
	@ManyToOne
	private Airport				airport;

	@Optional
	@Valid
	@ManyToOne
	private Airline				airline;

	@Optional
	@Valid
	@ManyToOne
	private Service				service;

	@Optional
	@Valid
	@ManyToOne
	private Flight				flight;

	@Optional
	@ValidNumber(min = 0, max = 10)
	@Automapped
	private Integer				score;

	@Optional
	@Valid
	@Automapped
	private Boolean				recommended;

}
