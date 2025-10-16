
package acme.entities.flights;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.constraints.ValidLongText;
import acme.constraints.ValidShortText;
import acme.entities.airlineManagers.AirlineManager;
import acme.entities.airports.Airport;
import acme.entities.legs.Leg;
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
	@Valid
	@Embedded
	private Money				cost;

	@Optional
	@ValidLongText
	@Automapped
	private String				description;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private AirlineManager		manager;

	@Transient
	@Valid
	@OneToMany(mappedBy = "flight")
	private List<Leg>			legs;


	@Transient
	private Date getDeparture() {
		return this.legs.stream().min(Comparator.comparing(Leg::getDeparture)).orElseGet(Leg::new).getDeparture();
	}

	@Transient
	private Date getArrival() {
		return this.legs.stream().max(Comparator.comparing(Leg::getArrival)).orElseGet(Leg::new).getArrival();
	}

	@Transient
	private Airport getOrigin() {
		return this.legs.stream().min(Comparator.comparing(Leg::getDeparture)).orElseGet(Leg::new).getOrigin();
	}

	@Transient
	private Airport getDestination() {
		return this.legs.stream().max(Comparator.comparing(Leg::getArrival)).orElseGet(Leg::new).getDestination();
	}

	@Transient
	private Integer getLayovers() {
		return this.legs.size() - 1;
	}

}
