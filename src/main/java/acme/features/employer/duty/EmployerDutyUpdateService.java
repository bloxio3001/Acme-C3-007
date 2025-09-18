/*
 * EmployerDutyUpdateService.java
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

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.realms.Employer;

@GuiService
public class EmployerDutyUpdateService extends AbstractGuiService<Employer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerDutyRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int dutyId;
		Job job;

		dutyId = super.getRequest().getData("id", int.class);
		job = this.repository.findJobByDutyId(dutyId);
		status = job != null && job.isDraftMode() && super.getRequest().getPrincipal().hasRealm(job.getEmployer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Duty duty;
		int id;

		id = super.getRequest().getData("id", int.class);
		duty = this.repository.findDutyById(id);

		super.getBuffer().addData(duty);
	}

	@Override
	public void bind(final Duty duty) {
		super.bindObject(duty, "title", "description", "workLoad", "moreInfo");
	}

	@Override
	public void validate(final Duty duty) {
		;
	}

	@Override
	public void perform(final Duty duty) {
		this.repository.save(duty);
	}

	@Override
	public void unbind(final Duty duty) {
		Dataset dataset;

		dataset = super.unbindObject(duty, "title", "description", "workLoad", "moreInfo");
		dataset.put("jobId", duty.getJob().getId());
		dataset.put("draftMode", duty.getJob().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
