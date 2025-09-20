
package acme.entities.airports;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.constraints.ValidAirport;
import acme.constraints.ValidIata;
import acme.datatypes.ContactDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@ValidAirport
public class Airport extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@NotBlank
	@Length(max = 50)
	@Automapped
	private String				name;

	@Mandatory
	@ValidIata
	@Automapped
	private String				code;

	@Mandatory
	@Valid
	@Automapped
	private OperationalScope	scope;

	@Mandatory
	@NotBlank
	@Length(max = 50)
	@Automapped
	private String				country;

	@Mandatory
	@NotBlank
	@Length(max = 50)
	@Automapped
	private String				city;

	@Optional
	@Valid
	@Automapped
	private ContactDetails		contactDetails;
}
