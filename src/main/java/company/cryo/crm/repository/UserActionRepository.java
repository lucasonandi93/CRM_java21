package company.cryo.crm.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import company.cryo.crm.model.UserAction;
import company.cryo.crm.model.UserGrant;

public interface UserActionRepository extends JpaRepository<UserAction, Integer> {

    @Query("SELECT ua FROM UserAction ua WHERE ua.method = :method")
    List<UserAction> findByMethod(@Param("method") String method);

    @Query("SELECT ua FROM UserAction ua WHERE ua.user.grantName = :grant")
    List<UserAction> findByGrant(@Param("grant") UserGrant grant);

    @Query("SELECT ua FROM UserAction ua WHERE ua.timestamp BETWEEN :startDate AND :endDate")
    List<UserAction> findByDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT ua FROM UserAction ua WHERE " +
           "(:method IS NULL OR ua.method = :method OR :method = '') AND " +
           "(:grant IS NULL OR ua.user.grantName = :grant)")
    List<UserAction> findUserActionsByCriteria(
    		@Param("method") String method,
            @Param("grant") UserGrant gran);

}
