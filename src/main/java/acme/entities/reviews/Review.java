
package acme.entities.reviews;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidLongText;
import acme.constraints.ValidShortText;
import acme.datatypes.Reviewable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	@Embedded
	private Reviewable			reviewable;

	@Optional
	@ValidNumber(min = 0, max = 10)
	@Automapped
	private Integer				score;

	@Optional
	@Valid
	@Automapped
	private Boolean				recommended;

}
