/*
 * AnyShoutCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.shout;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Any;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.shouts.Shout;

@GuiService
public class AnyShoutCreateService extends AbstractGuiService<Any, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyShoutRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Shout shout;

		shout = new Shout();

		super.getBuffer().addData(shout);
	}

	@Override
	public void bind(final Shout shout) {
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		super.bindObject(shout, "author", "text", "moreInfo");
		shout.setMoment(moment);
	}

	@Override
	public void validate(final Shout shout) {
		;
	}

	@Override
	public void perform(final Shout shout) {
		this.repository.save(shout);
	}

	@Override
	public void unbind(final Shout shout) {
		Dataset dataset;

		dataset = super.unbindObject(shout, "author", "text", "moreInfo");

		super.getResponse().addData(dataset);

	}

}
