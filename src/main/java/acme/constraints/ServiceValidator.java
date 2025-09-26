
package acme.constraints;

import java.time.LocalDate;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.services.Service;
import acme.entities.services.ServiceRepository;

@Validator
public class ServiceValidator extends AbstractValidator<ValidService, Service> {

	@Autowired
	private ServiceRepository repository;


	@Override
	public boolean isValid(final Service value, final ConstraintValidatorContext context) {

		assert context != null;

		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");

		else {
			// Last two digits of promotionCode corresponds to current year
			String yearDigits = String.valueOf(LocalDate.now().getYear()).substring(2);
			boolean correctPromoCode = value.getPromotionCode() != null && value.getPromotionCode().endsWith(yearDigits);

			super.state(context, correctPromoCode, "promotionCode", "acme.validation.service.promo.year.message");

			// Unique promotionCode
			Service existingService = this.repository.findServiceByPromotionCode(value.getPromotionCode());
			boolean uniquePromoCode = existingService == null || existingService.getPromotionCode() == null || existingService.equals(value);

			super.state(context, uniquePromoCode, "promotionCode", "acme.validation.service.promo.duplicated.message");

			// If promotionCode is present, discount is as well
			boolean existsDiscount = value.getPromotionCode() == null || value.getDiscount() != null;

			super.state(context, existsDiscount, "discount", "acme.validation.service.discount.mandatory.message");
		}

		return !super.hasErrors(context);
	}

}
