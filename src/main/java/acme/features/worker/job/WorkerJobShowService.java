/*
 * WorkerJobShowService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.worker.job;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.Company;
import acme.entities.jobs.Job;
import acme.realms.Worker;

@GuiService
public class WorkerJobShowService extends AbstractGuiService<Worker, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private WorkerJobRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Job job;
		Date currentMoment;

		id = super.getRequest().getData("id", int.class);
		job = this.repository.findJobById(id);
		currentMoment = MomentHelper.getCurrentMoment();
		status = !job.isDraftMode() && MomentHelper.isAfter(job.getDeadline(), currentMoment);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Job job;
		int id;

		id = super.getRequest().getData("id", int.class);
		job = this.repository.findJobById(id);

		super.getBuffer().addData(job);
	}

	@Override
	public void unbind(final Job job) {
		Collection<Company> contractors;
		SelectChoices choices;
		Dataset dataset;

		contractors = this.repository.findAllContractors();
		choices = SelectChoices.from(contractors, "name", job.getContractor());

		dataset = super.unbindObject(job, "ticker", "title", "deadline", "salary", "score", "moreInfo", "description", "draftMode");
		dataset.put("contractor", choices.getSelected().getKey());
		dataset.put("contractors", choices);

		super.getResponse().addData(dataset);
	}

}
