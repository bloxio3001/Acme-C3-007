/*
 * EmployerDutyListService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.realms.Employer;

@GuiService
public class EmployerDutyListService extends AbstractGuiService<Employer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerDutyRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int jobId;
		Job job;

		jobId = super.getRequest().getData("jobId", int.class);
		job = this.repository.findJobById(jobId);
		status = job != null && (!job.isDraftMode() || super.getRequest().getPrincipal().hasRealm(job.getEmployer()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Duty> duties;
		int jobId;

		jobId = super.getRequest().getData("jobId", int.class);
		duties = this.repository.findDutiesByJobId(jobId);

		super.getBuffer().addData(duties);
	}

	@Override
	public void unbind(final Duty duty) {
		Dataset dataset;

		dataset = super.unbindObject(duty, "title", "workLoad");
		super.addPayload(dataset, duty, "description", "moreInfo");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Duty> duties) {
		int jobId;
		Job job;
		final boolean showCreate;

		jobId = super.getRequest().getData("jobId", int.class);
		job = this.repository.findJobById(jobId);
		showCreate = job.isDraftMode() && super.getRequest().getPrincipal().hasRealm(job.getEmployer());

		super.getResponse().addGlobal("jobId", jobId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
