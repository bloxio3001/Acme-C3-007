/*
 * CustomerWhineCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.customer.whine;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.whines.Whine;
import acme.realms.Customer;

@GuiService
public class CustomerWhineCreateService extends AbstractGuiService<Customer, Whine> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerWhineRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Date moment;
		Customer customer;
		Whine whine;

		moment = MomentHelper.getCurrentMoment();
		customer = (Customer) super.getRequest().getPrincipal().getActiveRealm();

		whine = new Whine();
		whine.setMoment(moment);
		whine.setHeader("");
		whine.setDescription("");
		whine.setRedress("N/A");
		whine.setResolved(false);
		whine.setCustomer(customer);

		super.getBuffer().addData(whine);
	}

	@Override
	public void bind(final Whine whine) {
		super.bindObject(whine, "header", "description");
	}

	@Override
	public void validate(final Whine whine) {
		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "acme.validation.confirmation.message");
	}

	@Override
	public void perform(final Whine whine) {
		this.repository.save(whine);
	}

	@Override
	public void unbind(final Whine whine) {
		Dataset dataset;

		dataset = super.unbindObject(whine, "header", "description");

		super.getResponse().addData(dataset);

	}

}
