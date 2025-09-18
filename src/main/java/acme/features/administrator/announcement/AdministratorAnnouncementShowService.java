/*
 * AdministratorAnnouncementShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.announcements.Announcement;
import acme.entities.announcements.AnnouncementStatus;

@GuiService
public class AdministratorAnnouncementShowService extends AbstractGuiService<Administrator, Announcement> {

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
		int id;

		id = super.getRequest().getData("id", int.class);
		announcement = this.repository.findAnnouncementById(id);

		super.getBuffer().addData(announcement);
	}

	@Override
	public void unbind(final Announcement announcement) {
		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(AnnouncementStatus.class, announcement.getStatus());

		dataset = super.unbindObject(announcement, "title", "moment", "status", "text", "moreInfo");
		dataset.put("confirmation", false);
		dataset.put("readonly", true);
		dataset.put("statuses", choices);

		super.getResponse().addData(dataset);
	}

}
