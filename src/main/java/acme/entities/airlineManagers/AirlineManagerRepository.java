
package acme.entities.airlineManagers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AirlineManagerRepository extends AbstractRepository {

	@Query("SELECT am FROM AirlineManger am WHERE am.identifierNumber = :idNum")
	public AirlineManager findManagerByIdentifierNumber(String idNum);
}
