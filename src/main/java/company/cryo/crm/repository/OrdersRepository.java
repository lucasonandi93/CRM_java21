package company.cryo.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import company.cryo.crm.model.EstimateStatus;
import company.cryo.crm.model.Estimates;
import company.cryo.crm.model.OrderStatus;
import company.cryo.crm.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	
	 @Query("SELECT o FROM Orders o WHERE (:status IS NULL OR o.orderStatus = :status) AND " +
	            "(:label IS NULL OR lower(o.orderLabel) LIKE lower(concat('%', :label, '%'))) AND " +
	            "(:reference IS NULL OR str(o.id) LIKE lower(concat('%', :reference, '%'))) AND " +
	            "(:client IS NULL OR lower(o.customers.firstname) LIKE lower(concat('%', :client, '%')) OR lower(o.customers.lastname) LIKE lower(concat('%', :client, '%')))")
	    List<Orders> findByFilters(@Param("status") OrderStatus status,
	                               @Param("label") String label,
	                               @Param("reference") String reference,
	                               @Param("client") String client);  

}
