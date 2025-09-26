
package acme.entities.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ServiceRepository extends AbstractRepository {

	@Query("SELECT s FROM Service s WHERE s.promotionCode = :code")
	public Service findServiceByPromotionCode(String code);
}
