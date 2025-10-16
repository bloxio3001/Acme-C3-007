
package acme.constraints;

import java.io.Serializable;
import java.util.stream.Stream;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.reviews.Review;

@Validator
public class ReviewValidator extends AbstractValidator<ValidReview, Review> {

	@Override
	public boolean isValid(final Review value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			// One and only one reviewable
			Serializable[] revs = {
				value.getAirport(), value.getAirline(), value.getService(), value.getFlight()
			};

			boolean oneAndOnlyOneReviewable = Stream.of(revs).filter(x -> x != null).count() == 1;

			super.state(context, oneAndOnlyOneReviewable, "*", "acme.validation.review.reviewable.one.message");

			// Matches reviewableType
			boolean matchesReviewableType = false;
			String property = "*";
			switch (value.getReviewableType()) {
			case AIRLINE:
				matchesReviewableType = value.getAirline() != null;
				property = "airline";
				break;
			case AIRPORT:
				matchesReviewableType = value.getAirport() != null;
				property = "airport";
				break;
			case FLIGHT:
				matchesReviewableType = value.getFlight() != null;
				property = "flight";
				break;
			case SERVICE:
				matchesReviewableType = value.getService() != null;
				property = "service";
				break;
			default:
				break;
			}

			super.state(context, matchesReviewableType, property, "acme.validation.review.reviewable.match.message");
		}

		return !super.hasErrors(context);
	}

}
