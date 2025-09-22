
package acme.entities.airports;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidAirport;
import acme.datatypes.ContactDetails;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAirport
public class Airport extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				name;

	@Mandatory
	@ValidString(pattern = "^[A-Z]{3}$", message = "{acme.validation.iata.message}")
	@Column(unique = true)
	@Automapped
	private String				code;

	@Mandatory
	@Valid
	@Automapped
	private OperationalScope	scope;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				country;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				city;

	@Optional
	@Valid
	@Automapped
	private ContactDetails		contactDetails;
}
