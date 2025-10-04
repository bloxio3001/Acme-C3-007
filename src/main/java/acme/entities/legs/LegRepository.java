
package acme.entities.legs;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface LegRepository extends AbstractRepository {

	@Query("SELECT l FROM Leg l WHERE l.flightNumber = :flightNumber")
	public Leg findLegByFlightNumber(String flightNumber);
}
