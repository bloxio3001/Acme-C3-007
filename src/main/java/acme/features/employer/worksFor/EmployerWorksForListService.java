/*
 * EmployerWorksForListService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.employer.worksFor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.companies.WorksFor;
import acme.realms.Employer;

@GuiService
public class EmployerWorksForListService extends AbstractGuiService<Employer, WorksFor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EmployerWorksForRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int proxyId;
		Collection<WorksFor> worksFors;

		proxyId = super.getRequest().getPrincipal().getActiveRealm().getId();
		worksFors = this.repository.findWorksForByProxyId(proxyId);

		super.getBuffer().addData(worksFors);
	}

	@Override
	public void unbind(final WorksFor worksFor) {
		Dataset dataset;

		dataset = super.unbindObject(worksFor, "roles");
		dataset.put("contractor", worksFor.getContractor().getName());
		super.addPayload(dataset, worksFor, "contractor.description", "contractor.moreInfo");

		super.getResponse().addData(dataset);
	}

}
