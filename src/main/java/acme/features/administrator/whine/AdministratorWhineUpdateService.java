/*
 * AdministratorWhineUpdateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.whine;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.helpers.StringHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.whines.Whine;

@GuiService
public class AdministratorWhineUpdateService extends AbstractGuiService<Administrator, Whine> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorWhineRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int whineId;
		Whine whine;

		whineId = super.getRequest().getData("id", int.class);
		whine = this.repository.findWhineById(whineId);
		status = whine != null && StringHelper.isEqual(whine.getRedress(), "N/A", true);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int whineId;
		Whine whine;

		whineId = super.getRequest().getData("id", int.class);
		whine = this.repository.findWhineById(whineId);

		super.bindObject(whine, "redress");

		super.getBuffer().addData(whine);
	}

	@Override
	public void bind(final Whine whine) {
		super.bindObject(whine, "header", "description", "redress");
	}

	@Override
	public void validate(final Whine whine) {
		{
			boolean confirmation;

			confirmation = super.getRequest().getData("confirmation", boolean.class);
			super.state(confirmation, "confirmation", "acme.validation.confirmation.message");
		}

		{
			boolean fullRedress;

			fullRedress = !StringHelper.isEqual(whine.getRedress(), "N/A", true);
			super.state(fullRedress, "redress", "acme.validation.whine.redress.message");
		}
	}

	@Override
	public void perform(final Whine whine) {
		whine.setResolved(true);
		this.repository.save(whine);
	}

	@Override
	public void unbind(final Whine whine) {
		Dataset dataset;

		dataset = super.unbindObject(whine, "header", "description", "redress", "resolved");

		super.getResponse().addData(dataset);
	}

}
