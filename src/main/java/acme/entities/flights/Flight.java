
package acme.entities.flights;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
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

	@Mandatory
	@ValidLongText
	@Automapped
	private String				description;

	@Mandatory
	@Valid
	@Transient
	private Date				departure;

	@Mandatory
	@Valid
	@Transient
	private Date				arrival;

	@Mandatory
	@Valid
	@Transient
	private String				origin;

	@Mandatory
	@Valid
	@Transient
	private String				destination;

	@Mandatory
	@Valid
	@Transient
	private Integer				layovers;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private AirlineManager		manager;

}
