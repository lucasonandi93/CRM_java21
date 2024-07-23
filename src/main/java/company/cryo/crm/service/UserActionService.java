package company.cryo.crm.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import company.cryo.crm.config.MonUserDetails; // Asegúrate de importar MonUserDetails aquí
import company.cryo.crm.model.UserAction;
import company.cryo.crm.model.UserGrant;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.UserActionRepository;
import company.cryo.crm.repository.UsersRepository; // Asegúrate de importar UsersRepository aquí

@Service
public class UserActionService {

    @Autowired
    private UserActionRepository userActionRepository;
    
    @Autowired
    private UsersRepository usersRepository;
    
    public void logUserAction(String method, String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MonUserDetails userDetails = (MonUserDetails) authentication.getPrincipal();
        Users user = usersRepository.findById(userDetails.getId()).orElse(null);
        
        UserAction logEntry = new UserAction();
        logEntry.setUser(user); 
        logEntry.setMethod(method);
        logEntry.setAction(action);
        logEntry.setTimestamp(Timestamp.valueOf(java.time.LocalDateTime.now()));
        
        userActionRepository.save(logEntry);
    }

    public List<UserAction> getAllUserActions() {
        return userActionRepository.findAll();
    }

    public List<UserAction> findUserActionsByMethod(String method) {
        return userActionRepository.findByMethod(method);
    }

    public List<UserAction> findUserActionsByGrant(UserGrant grant) {
        return userActionRepository.findByGrant(grant);
    }

    public List<UserAction> findUserActionsByDateRange(Timestamp startDate, Timestamp endDate) {
        return userActionRepository.findByDateRange(startDate, endDate);
    }

    public Optional<UserAction> getUserActionById(Integer id) {
        return userActionRepository.findById(id);
    }

	public List<UserAction> findUserActionsByCriteria(String method, UserGrant grant) {
		 System.out.println("parametros en servicio: method = "+method+ " grant = "+grant);
		return userActionRepository.findUserActionsByCriteria(method, grant);
	}
}
