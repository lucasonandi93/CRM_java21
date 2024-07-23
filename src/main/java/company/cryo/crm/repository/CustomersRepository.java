package company.cryo.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import company.cryo.crm.model.CustomerStatus;
import company.cryo.crm.model.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer> {

	/**
	 * Get a customer by id
	 * @param
	 * @return
	 */
//	Optional<Customers> findById(Integer id);

	@Query("SELECT c FROM Customers c WHERE (:customerStatus IS NULL OR c.customerStatus = :customerStatus) AND (:company IS NULL OR lower(c.company) LIKE lower(concat('%',:company,'%')))")
	List<Customers> findByStatusAndByCompany(@Param("customerStatus") CustomerStatus customerStatus, @Param("company") String company);


}
