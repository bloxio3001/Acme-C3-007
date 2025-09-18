/*
 * AdministratorAnnouncementCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.announcement;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.announcements.Announcement;
import acme.entities.announcements.AnnouncementStatus;

@GuiService
public class AdministratorAnnouncementCreateService extends AbstractGuiService<Administrator, Announcement> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorAnnouncementRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Announcement announcement;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		announcement = new Announcement();
		announcement.setTitle("");
		announcement.setMoment(moment);
		announcement.setStatus(AnnouncementStatus.INFO);
		announcement.setText("");
		announcement.setMoreInfo("");

		super.getBuffer().addData(announcement);
	}

	@Override
	public void bind(final Announcement announcement) {
		super.bindObject(announcement, "title", "status", "text", "moreInfo");
	}

	@Override
	public void validate(final Announcement announcement) {
		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "acme.validation.confirmation.message");
	}

	@Override
	public void perform(final Announcement announcement) {
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		announcement.setMoment(moment);
		this.repository.save(announcement);
	}

	@Override
	public void unbind(final Announcement announcement) {
		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(AnnouncementStatus.class, announcement.getStatus());

		dataset = super.unbindObject(announcement, "title", "status", "text", "moreInfo");
		dataset.put("confirmation", false);
		dataset.put("readonly", false);
		dataset.put("statuses", choices);

		super.getResponse().addData(dataset);
	}

}
