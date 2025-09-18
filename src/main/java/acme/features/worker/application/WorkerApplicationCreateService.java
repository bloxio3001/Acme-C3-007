/*
 * WorkerApplicationCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.worker.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.jobs.Application;
import acme.entities.jobs.ApplicationStatus;
import acme.entities.jobs.Job;
import acme.realms.Worker;

@GuiService
public class WorkerApplicationCreateService extends AbstractGuiService<Worker, Application> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private WorkerApplicationRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int jobId;
		Job job;
		Date currentDate;

		jobId = super.getRequest().getData("jobId", int.class);
		job = this.repository.findJobById(jobId);
		currentDate = MomentHelper.getCurrentMoment();
		status = job != null && !job.isDraftMode() && MomentHelper.isAfter(job.getDeadline(), currentDate);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Application application;
		Worker worker;
		int jobId;
		Job job;
		String ticker;
		Date moment;

		worker = (Worker) super.getRequest().getPrincipal().getActiveRealm();
		jobId = super.getRequest().getData("jobId", int.class);
		job = this.repository.findJobById(jobId);
		ticker = RandomHelper.nextUUID().toString();
		moment = MomentHelper.getCurrentMoment();

		application = new Application();
		application.setTicker(ticker);
		application.setMoment(moment);
		application.setStatus(ApplicationStatus.PENDING);
		application.setStatement("");
		application.setSkills(worker.getSkills());
		application.setQualifications(worker.getQualifications());
		application.setJob(job);
		application.setWorker(worker);

		super.getBuffer().addData(application);
	}

	@Override
	public void bind(final Application application) {
		super.bindObject(application, "ticker", "statement", "skills", "qualifications");
	}

	@Override
	public void validate(final Application application) {
		;
	}

	@Override
	public void perform(final Application application) {
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		application.setMoment(moment);
		this.repository.save(application);
	}

	@Override
	public void unbind(final Application application) {
		Dataset dataset;

		dataset = super.unbindObject(application, "ticker", "statement", "skills", "qualifications");
		dataset.put("jobId", application.getJob().getId());

		super.getResponse().addData(dataset);
	}

}
