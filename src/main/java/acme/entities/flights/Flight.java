
package acme.entities.flights;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidLongText;
import acme.constraints.ValidShortText;
import acme.entities.airlineManagers.AirlineManager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Flight extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidShortText
	@Automapped
	private String				tag;

	@Mandatory
	@Valid
	@Automapped
	private Boolean				selfTransfer;

	@Mandatory
	@ValidNumber(fraction = 2)
	@Automapped
	private Double				cost;

	@Optional
	@ValidLongText
	@Automapped
	private String				description;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private AirlineManager		manager;


	@Transient
	private Date getDeparture() {
		return null; //TBI
	}

	@Transient
	private Date getArrival() {
		return null; //TBI
	}

	@Transient
	private String getOrigin() {
		return null; //TBI
	}

	@Transient
	private String getDestination() {
		return null; //TBI
	}

	@Transient
	private Integer getLayovers() {
		return null; //TBI
	}

}
