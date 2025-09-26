
package acme.entities.services;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidService;
import acme.entities.airports.Airport;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidService
public class Service extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				name;

	@Mandatory
	@ValidUrl
	@Automapped
	private String				picture;

	@Mandatory
	@ValidNumber(fraction = 2)
	@Automapped
	private Double				dwell;

	@Optional
	@ValidString(pattern = "^[A-Z]{4}-[0-9]{2}$", message = "{acme.validation.service.promo.pattern.message}")
	@Column(unique = true)
	@Automapped
	private String				promotionCode;

	@Optional
	@ValidNumber(fraction = 2)
	@Automapped
	private Double				discount;

	@ManyToOne(optional = false)
	@Valid
	@Automapped
	private Airport				airport;
}
