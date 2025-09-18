/*
 * EmployerJobPublishService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.job;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.Company;
import acme.entities.companies.WorksFor;
import acme.entities.jobs.Job;
import acme.realms.Employer;

@GuiService
public class EmployerJobPublishService extends AbstractGuiService<Employer, Job> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerJobRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int jobId;
		Job job;
		Employer employer;

		jobId = super.getRequest().getData("id", int.class);
		job = this.repository.findJobById(jobId);
		employer = job == null ? null : job.getEmployer();
		status = job != null && job.isDraftMode() && super.getRequest().getPrincipal().hasRealm(employer);

		if (status) {
			String method;
			int proxyId, contractorId;
			WorksFor worksFor;

			method = super.getRequest().getMethod();

			if (method.equals("GET"))
				status = true;
			else {
				proxyId = super.getRequest().getPrincipal().getActiveRealm().getId();
				contractorId = super.getRequest().getData("contractor", int.class);
				worksFor = this.repository.findWorksForByProxyAndContractorId(proxyId, contractorId);
				status = contractorId == 0 || worksFor != null;
			}
		}

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
	public void bind(final Job job) {
		int contractorId;
		Company contractor;

		contractorId = super.getRequest().getData("contractor", int.class);
		contractor = this.repository.findContractorById(contractorId);

		super.bindObject(job, "ticker", "title", "deadline", "salary", "score", "moreInfo", "description");
		job.setContractor(contractor);
	}

	@Override
	public void validate(final Job job) {
		{
			boolean correctWorkload;

			correctWorkload = job.getWorkLoad() == 100.00;

			super.state(correctWorkload, "*", "acme.validation.job.workload.message");
		}
	}

	@Override
	public void perform(final Job job) {
		job.setDraftMode(false);
		this.repository.save(job);
	}

	@Override
	public void unbind(final Job job) {
		int employerId;
		Collection<Company> contractors;
		SelectChoices choices;
		Dataset dataset;

		employerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		contractors = this.repository.findContractorsByProxyId(employerId);
		choices = SelectChoices.from(contractors, "name", job.getContractor());

		dataset = super.unbindObject(job, "ticker", "title", "deadline", "salary", "score", "moreInfo", "description", "draftMode");
		dataset.put("contractor", choices.getSelected().getKey());
		dataset.put("contractors", choices);

		super.getResponse().addData(dataset);
	}

}
