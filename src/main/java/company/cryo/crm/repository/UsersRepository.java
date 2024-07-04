package company.cryo.crm.repository;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import company.cryo.crm.model.UserGrant;
import company.cryo.crm.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {


	Users findByEmail(String mail);

	@Query("SELECT u FROM Users u WHERE (:userGrant IS NULL OR u.grantName = :userGrant) AND (:nom IS NULL OR lower(u.firstname) LIKE lower(concat('%', :nom, '%')) OR lower(u.lastname) LIKE lower(concat('%', :nom, '%')))")
    List<Users> findByGrantAndByNom(UserGrant userGrant, String nom);
	

}


