
package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.Valid;

import acme.client.components.basis.AbstractDatatype;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class ContactDetails extends AbstractDatatype {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Optional
	@ValidUrl
	@Automapped
	private String				website;

	@Optional
	@ValidEmail
	@Automapped
	private String				email;

	@Optional
	@Valid
	@Automapped
	private Phone				phone;

}
