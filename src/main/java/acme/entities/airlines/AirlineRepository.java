
package acme.entities.airlines;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AirlineRepository extends AbstractRepository {

	@Query("SELECT a FROM Airline a WHERE a.code = :code")
	public Airline findAirlineByIata(String code);
}
