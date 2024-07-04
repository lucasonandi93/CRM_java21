package company.cryo.crm.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import company.cryo.crm.dto.UsersDto;
import company.cryo.crm.form.UserForm;
import company.cryo.crm.model.UserGrant;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.UsersRepository;
import company.cryo.crm.service.UserActionService;
import company.cryo.crm.service.UserService;
import jakarta.servlet.http.HttpSession;


@Controller
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserActionService userActionService;
    
	@Autowired
	public PasswordEncoder passwordEncoder;

    public static final String SESSION_USERS = "users";

    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/listUsers")
    public String listUsers(@RequestParam(name ="grant",  required = false) UserGrant grant,
            @RequestParam(name="nom", required = false) String nom , Model model, HttpSession session) {
        
    	
    	
    	List<UsersDto> usersList;

        if (nom != null || grant != null) {
        	try {
        		usersList = userService.getUsersByFilter(grant, nom);
			} catch (Exception e) {
				 model.addAttribute("message", "Error charge LIST Users "+e.getMessage());
				 return "redirect:/errorPage";
			}
        	
        } else {
        	try {
        		usersList = userService.showListeUsers();
			} catch (Exception e) {
				model.addAttribute("message", "Error charge LIST Users "+e.getMessage());
				 return "redirect:/errorPage";
			}
        	
        }
        model.addAttribute("usersList", usersList);
        model.addAttribute("userGrant", UserGrant.values());
        model.addAttribute("nom", nom);
        model.addAttribute("grant", grant);

        return "/listUsers";
    }
    
    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/userDetails/{userId}")
    public String viewUser(@PathVariable("userId") Integer userId, Model model) {
    	try {

            Users user = usersRepository.findById(userId).orElse(null);
            if (user == null) {
                return "redirect:/listUsers";
            }
            model.addAttribute("user", user);
		} catch (Exception e) {
			model.addAttribute("message", "Error charge UPDATE Users "+e.getMessage());
			return "redirect:/listUsers";
		}
        
        return "/userDetails";
    }
    
    //controller désactiver un user
    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/warning/{userId}")
    public String showWarningPage(@PathVariable("userId") Integer userId, Model model) {
    	try {
    		Users user = usersRepository.findById(userId).orElse(null);
            if (user == null) {
                return "redirect:/listUsers";
            }
            model.addAttribute("userName", user.getFirstname() + " " + user.getLastname());
            model.addAttribute("userId", userId);
            return "/warning"; 
		} catch (Exception e) {
			model.addAttribute("message", "Error charge UPDATE Users "+e.getMessage());
			return "redirect:/listUsers";
		}
    }

    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @PostMapping("/warning")
    public String disableUser(@RequestParam("userId") Integer userId, Model model, HttpSession session) {
    	try {
    		 Users user = usersRepository.findById(userId).orElse(null);
    	        if (user == null) {
    	            return "redirect:/listUsers";
    	        }
    	        // Mettre à jour l'utilisateur pour le désactiver
    	        user.setActive(false); 
    	        usersRepository.save(user);
    	        userActionService.logUserAction("User","Changement de status User N°"+userId);
    	        
		} catch (Exception e) {
			model.addAttribute("message", "Error charge UPDATE Users "+e.getMessage());
			return "redirect:/listUsers";
		}
    	return "redirect:/listUsers";
    }

  // controller modifier un utilisateur 
    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/updateUser")
    public String showUpdateUserForm(@RequestParam("userId") Integer userId, UserForm userForm, Model model) {
    	
    	try {
    		 Users user = usersRepository.findById(userId).orElse(null);
    	        if (user == null) {
    	            return "redirect:/listUsers";
    	        }
    	        userForm.setId(userId);
    	        userForm.setFirstname(user.getFirstname());
    	        userForm.setLastname(user.getLastname());
    	        userForm.setEmail(user.getEmail());
    	        userForm.setGrantName(user.getGrantName());
    	        userForm.setActive(user.getActive());
    	        model.addAttribute("userForm", userForm);
		} catch (Exception e) {
			model.addAttribute("message", "Error charge UPDATE Users "+e.getMessage());
		}
       
        return "updateUser";
    }

    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @PostMapping("/updateUser")
    public String updateUser(
                             @ModelAttribute("userForm") @Validated UserForm userForm,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updateUser";
        }
        List<String> changes = new ArrayList<>();
        Integer userId = userForm.getId();
        System.out.println(userForm.toString());
        
        try {
        	 // Récupérez l'utilisateur existant depuis la base de données
            Users userToUpdate = usersRepository.findById(userId).orElse(null);
            if (userToUpdate == null) {
                return "redirect:/listUsers";
            }
            if (!Objects.equals(userToUpdate.getFirstname(), userForm.getFirstname())) {
                changes.add("First Name: " + userToUpdate.getFirstname() + " -> " + userForm.getFirstname());
                userToUpdate.setFirstname(userForm.getFirstname());
            }
            if (!Objects.equals(userToUpdate.getLastname(), userForm.getLastname())) {
                changes.add("Last Name: " + userToUpdate.getLastname() + " -> " + userForm.getLastname());
                userToUpdate.setLastname(userForm.getLastname());
            }
            if (!Objects.equals(userToUpdate.getEmail(), userForm.getEmail())) {
                changes.add("Email: " + userToUpdate.getEmail() + " -> " + userForm.getEmail());
                userToUpdate.setEmail(userForm.getEmail());
            }
            if (!Objects.equals(userToUpdate.getGrantName(), userForm.getGrantName())) {
                changes.add("Grant Name: " + userToUpdate.getGrantName() + " -> " + userForm.getGrantName());
                userToUpdate.setGrantName(userForm.getGrantName());
            }
            // Mettez à jour les propriétés de l'utilisateur existant avec les données du formulaire
            userToUpdate.setFirstname(userForm.getFirstname());
            userToUpdate.setLastname(userForm.getLastname());
            userToUpdate.setEmail(userForm.getEmail());
            userToUpdate.setGrantName(userForm.getGrantName());
            //userToUpdate.setCreatedAt(userForm.getCreatedAt()); 
            //userToUpdate.setUpdatedAt(userForm.getUpdatedAt());

            // Enregistrez les modifications dans la base de données
            usersRepository.save(userToUpdate);
            String changesMessage = "Update de User " + userToUpdate.getId() + String.join(", ", changes);
            userActionService.logUserAction("User", changesMessage);
            // Ajoutez l'utilisateur mis à jour à l'objet modèle pour affichage 
            model.addAttribute("user", userToUpdate);
            model.addAttribute("userId", userId);
		} catch (Exception e) {
			model.addAttribute("message", "Error charge UPDATE Users "+e.getMessage());
		}
        return "redirect:/listUsers";
    }
    
    //controller créer un utilisateur
    @PreAuthorize("hasAuthority('RESPONSABLE')")
    @GetMapping("/createUser")
    public String showCreateUserForm(UserForm userForm, Model model) {
        model.addAttribute("userForm", new UserForm());
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("userForm") @Validated UserForm userForm,
                             BindingResult bindingResult, Model model) {
       

            try {
            	 Users user = usersRepository.findById(1).orElse(null);
             	if (!bindingResult.hasErrors()) {
                     UsersDto userDto = new UsersDto();
                     String hashedPassword = passwordEncoder.encode(userForm.getUserPassword());
                     userDto.setFirstname(userForm.getFirstname());
                     userDto.setLastname(userForm.getLastname());
                     userDto.setEmail(userForm.getEmail());
                     userDto.setUserPassword(hashedPassword);
                     userDto.setGrantName(userForm.getGrantName());
                     userDto.setActive(userForm.getActive());
                     userService.saveUser(userDto);
                     userActionService.logUserAction("User", "Création de l'utilisateur "+ userDto.getFirstname()+" "+userDto.getLastname());
             	 } else {

                     System.out.println("ERROR");
                     return "createUser";
                 }
			} catch (Exception e) {
		           System.out.println("CATCH");
		           System.out.println(e.getMessage());
				model.addAttribute("message", "Error charge Create Users "+e.getMessage());
			}

            return "redirect:/listUsers";
       
    }
    
    
    
   /* @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Integer userId, Model model, HttpSession session) {
        if (userId == null) {
            return "redirect:/listUsers";
        }
        usersRepository.deleteById(userId);
        session.removeAttribute(SESSION_USERS);
        model.addAttribute("message", "Suppression réussie.");
        return listUsers(model, session);
    }*/
    
    

   
}