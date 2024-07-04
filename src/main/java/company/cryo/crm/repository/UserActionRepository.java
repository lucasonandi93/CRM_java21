package company.cryo.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import company.cryo.crm.model.UserAction;
import company.cryo.crm.model.UserGrant;

public interface UserActionRepository extends JpaRepository<UserAction, Integer> {
    
	@Query("SELECT ua FROM UserAction ua " +
	           "WHERE (:method IS NULL OR ua.method = :method)")
	    List<UserAction> findUserActionsByFilters( @Param("method") String method);
}
