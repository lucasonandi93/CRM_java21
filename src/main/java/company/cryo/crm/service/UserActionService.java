package company.cryo.crm.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    public Optional<UserAction> getUserActionById(Integer id) {
        return userActionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<UserAction> findUserActionsByFilters(String method) {
        return userActionRepository.findUserActionsByFilters(method);
    }

}
