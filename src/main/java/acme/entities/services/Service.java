
package acme.entities.services;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidPromoCode;
import acme.constraints.ValidService;
import acme.constraints.ValidShortText;
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
	@ValidShortText
	@Automapped
	private String				name;

	@Mandatory
	@ValidUrl
	@Automapped
	private String				picture;

	@Mandatory
	@Valid
	@Embedded
	private Money				dwell;

	@Optional
	@ValidPromoCode
	@Column(unique = true)
	@Automapped
	private String				promotionCode;

	@Optional
	@Valid
	@Embedded
	private Money				discount;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airport				airport;
}
