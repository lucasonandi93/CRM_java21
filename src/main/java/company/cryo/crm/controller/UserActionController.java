package company.cryo.crm.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import company.cryo.crm.model.UserAction;
import company.cryo.crm.model.UserGrant;
import company.cryo.crm.service.UserActionService;

@Controller
public class UserActionController {

    private final UserActionService userActionService;

    @Autowired
    public UserActionController(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/listUserActions")
    public String listUserActions(
            @RequestParam(name = "method", required = false) String method,
            @RequestParam(name = "grant", required = false) UserGrant grant,
            @RequestParam(name = "dateFilter", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFilter,
            Model model) {

        List<UserAction> userActions;

        // Debugging message to check parameters received
        System.out.println("[DEBUG] Parameters received: method=" + method + ", grant=" + grant + ", dateFilter=" + dateFilter);

        // Retrieve user actions based on criteria
        if (method != null && !method.isEmpty() || grant != null || dateFilter != null) {
            userActions = userActionService.findUserActionsByCriteria(method, grant);

            if (dateFilter != null) {
                LocalDateTime startDate = dateFilter.atStartOfDay();
                LocalDateTime endDate = dateFilter.atTime(LocalTime.MAX);

                Timestamp startTimestamp = Timestamp.valueOf(startDate);
                Timestamp endTimestamp = Timestamp.valueOf(endDate);

                // Filter by date within the specified range
                userActions = userActions.stream()
                                         .filter(action -> action.getTimestamp().after(startTimestamp) && action.getTimestamp().before(endTimestamp))
                                         .collect(Collectors.toList());
            }
        } else {
            // If no filters are applied, retrieve all user actions
            userActions = userActionService.getAllUserActions();
        }

        // Add attributes to the model for Thymeleaf rendering
        model.addAttribute("userActions", userActions);
        model.addAttribute("method", method);
        model.addAttribute("grant", grant);
        model.addAttribute("dateFilter", dateFilter);

        return "listUserActions";
    }
    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/userAction/{id}")
    public String viewUserAction(@PathVariable("id") Integer id, Model model) {
    	
    	try {
    		UserAction userAction = userActionService.getUserActionById(id).orElse(null);
    		if (userAction == null) {
                model.addAttribute("message", "User action not found.");
                return "redirect:/listUserActions";
            }
    		  model.addAttribute("userAction", userAction);
    	       return "viewUserAction";
    	       
		} catch (Exception e) {
			model.addAttribute("message", "ERROR: "+e.getMessage());
			return "redirect:/listUserActions";
		}
      
    }
}
