
package acme.entities.airports;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AirportRepository extends AbstractRepository {

	@Query("SELECT a FROM Airport a WHERE a.code = :code")
	public Airport findAirportByIata(String code);
}
