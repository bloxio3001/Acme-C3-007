/*
 * AuthenticatedWorkerCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.worker;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Customer;
import acme.realms.Worker;

@GuiService
public class AuthenticatedWorkerCreateService extends AbstractGuiService<Authenticated, Worker> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedWorkerRepository repository;

	// AbstractService inteface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !this.getRequest().getPrincipal().hasRealmOfType(Worker.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Worker worker;
		int userAccountId;
		UserAccount userAccount;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		worker = new Worker();
		worker.setUserAccount(userAccount);

		super.getBuffer().addData(worker);
	}

	@Override
	public void bind(final Worker worker) {
		super.bindObject(worker, "qualifications", "skills", "creditCard");
	}

	@Override
	public void validate(final Worker worker) {
		;
	}

	@Override
	public void perform(final Worker worker) {
		Customer customer;

		this.repository.save(worker);
		customer = this.repository.findCustomerByUserAccountId(worker.getUserAccount().getId());
		if (customer == null) {
			customer = new Customer();
			customer.setUserAccount(worker.getUserAccount());
			this.repository.save(customer);
		}
	}

	@Override
	public void unbind(final Worker worker) {
		Dataset dataset;

		dataset = super.unbindObject(worker, "qualifications", "skills", "creditCard");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
