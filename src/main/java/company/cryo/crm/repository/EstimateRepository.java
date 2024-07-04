package company.cryo.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import company.cryo.crm.model.EstimateStatus;
import company.cryo.crm.model.Estimates;

public interface EstimateRepository extends JpaRepository<Estimates, Integer> {
	
    List<Estimates> findByEstimateStatus(EstimateStatus estimateStatus);
    

        @Query("SELECT e FROM Estimates e WHERE (:status IS NULL OR e.estimateStatus = :status) AND " +
               "(:label IS NULL OR lower(e.estimateLabel) LIKE lower(concat('%', :label, '%'))) AND " +
               "(:reference IS NULL OR str(e.id) LIKE lower(concat('%', :reference, '%'))) AND " +
               "(:client IS NULL OR lower(e.customers.firstname) LIKE lower(concat('%', :client, '%')) OR lower(e.customers.lastname) LIKE lower(concat('%', :client, '%')))")
        List<Estimates> findByFilters(@Param("status") EstimateStatus status,
                                      @Param("label") String label,
                                      @Param("reference") String reference,
                                      @Param("client") String client);
    

}
