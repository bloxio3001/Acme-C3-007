
package acme.entities.airlineManagers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidAirlineManager;
import acme.constraints.ValidManagerIdentifier;
import acme.entities.airlines.Airline;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAirlineManager
public class AirlineManager extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@Column(unique = true)
	@ValidManagerIdentifier
	@Automapped
	private String				identifierNumber;

	@Mandatory
	@ValidNumber
	@Automapped
	private Integer				yearsOfExperience;

	@Mandatory
	@ValidMoment(past = true, min = "1900/01/01 00:00")
	@Temporal(TemporalType.TIMESTAMP)
	private Date				birthday;

	@Optional
	@ValidUrl
	@Automapped
	private String				picture;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airline				airline;

}
