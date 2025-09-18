/*
 * AdministratorCompanyDeleteService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.company;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.Company;

@GuiService
public class AdministratorCompanyDeleteService extends AbstractGuiService<Administrator, Company> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorCompanyRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Company company;
		int id;

		id = super.getRequest().getData("id", int.class);
		company = this.repository.findCompanyById(id);

		super.getBuffer().addData(company);
	}

	@Override
	public void bind(final Company company) {
		super.bindObject(company, "name", "description", "moreInfo");
	}

	@Override
	public void validate(final Company company) {
		boolean status;
		int id, numberProxies, numberJobs;

		id = super.getRequest().getData("id", int.class);
		numberProxies = this.repository.countProxiesByContractorId(id);
		numberJobs = this.repository.countJobsByContractorId(id);

		status = numberProxies == 0 && numberJobs == 0;

		super.state(status, "*", "administrator.company.delete.company-linked");
	}

	@Override
	public void perform(final Company company) {
		this.repository.delete(company);
	}

	@Override
	public void unbind(final Company company) {
		Dataset dataset;

		dataset = super.unbindObject(company, "name", "description", "moreInfo");

		super.getResponse().addData(dataset);
	}

}
