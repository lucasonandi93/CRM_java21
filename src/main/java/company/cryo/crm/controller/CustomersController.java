package company.cryo.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import company.cryo.crm.dto.CustomersDto;
import company.cryo.crm.form.CustomerForm;
import company.cryo.crm.model.CustomerStatus;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.UsersRepository;
import company.cryo.crm.service.CustomersService;
import company.cryo.crm.service.UserActionService;

@Controller
public class CustomersController {

    @Autowired
    private CustomersService customersService;
    
    @Autowired
    private UserActionService userActionService;
    
    @Autowired 
    private UsersRepository userRepository;
    
    
    @PreAuthorize("hasAuthority('SERVICE_CLIENT')") 
    @GetMapping("/createCustomer")
	public String showForm(@ModelAttribute("customerForm")
			CustomerForm customerForm,
			Model model) {
    	//TODO remplacer userRepository par userService
    	List<Users> users = userRepository.findAll();	
      	     	
    	model.addAttribute("users", users);
    	
		 List<CustomerStatus> customerStatus = new ArrayList <>();
         for (CustomerStatus status : CustomerStatus.values()) {
      	   customerStatus.add(status);
         }
          model.addAttribute("customerStatus", customerStatus);
          
       	  
		return "createCustomer";
	}

    @PreAuthorize("hasAuthority('SERVICE_CLIENT')")
    @PostMapping(path="/createCustomer")
    public String checkCustomerInfo(@ModelAttribute("customerForm")
                                    @Validated CustomerForm customerForm,
                                    BindingResult bindingResult,
                                    Model model) {

        if (!bindingResult.hasErrors()) {
        	Users user;
        	if (null == customerForm.getUserId()) {
        		user = null;
        	 }
        	else {
        	 user = userRepository.findById(
        			customerForm.getUserId())
        			.orElse(null);}
        	 
            CustomersDto customerDto = new CustomersDto();
            
            customerDto.setFirstname(customerForm.getFirstname());
            customerDto.setLastname(customerForm.getLastname());
            customerDto.setEmail(customerForm.getEmail());
            customerDto.setCompany(customerForm.getCompany());
            customerDto.setOfficePhone(customerForm.getOfficePhone());
            customerDto.setMobilePhone(customerForm.getOfficePhone());
            customerDto.setCustomerStatus(customerForm.getCustomerStatus());
            customerDto.setGuarantee(customerForm.getGuarantee());
            customerDto.setCustomerComment(customerForm.getCustomerComment());  
            customerDto.setUsers(user);
            customersService.create(customerDto);
            userActionService.logUserAction("Client", "Creacion de Customer "+ customerDto.getFirstname()+ " "+customerDto.getLastname());
                    
            return "redirect:/listecustomers";
        } else {
        	return showForm(customerForm, model);
        }
    }

    /**
     * @param customerStatus
     * @param company
     * @param model
     * @return Liste des clients par filtré par entreprise : "commpany" et par status : "customerStatus"
     */

    
    @GetMapping("/listecustomers")
    @PreAuthorize("hasAuthority('SERVICE_CLIENT')")
    public String showListeCustomers(@RequestParam(name ="status",  required = false) CustomerStatus customerStatus,
                                         @RequestParam(name="company", required = false) String company , Model model) {

        List<CustomersDto> customersList;

        if (company != null || customerStatus != null) {
            customersList = customersService.getCustumersByFilter(customerStatus, company);
        } else {
            customersList = customersService.showListeCustomers();
        }
        for (CustomersDto customerDto: customersList) {
        	Integer customerId = customerDto.getId();
        	model.addAttribute("customerId", customerId);
        	
        }
        model.addAttribute("customersList", customersList);
        model.addAttribute("customerStatus", CustomerStatus.values());
        model.addAttribute("status", customerStatus);
        model.addAttribute("company", company);

        return "listecustomers";
    }
    
    @PreAuthorize("hasAuthority('SERVICE_CLIENT')")
    @GetMapping("/customerDetails/{customerId}")
    public String viewUser(@PathVariable("customerId") Integer customerId, Model model) {
        CustomersDto customer = customersService.findCustomerById(customerId);
        if (customer == null) {
            return "redirect:/listCustomers";
        }
        model.addAttribute("customer", customer);
        return "customerDetails";
    }

    @PreAuthorize("hasAuthority('SERVICE_CLIENT')")
    @GetMapping("/updateCustomer")
    public String showUpdateForm(@RequestParam("customerId")
    	Integer customerId,
    	CustomerForm customerForm,
    	BindingResult bindingResult,
    	Model model) {
    	
    	 
    	  
    	 List<CustomerStatus> customerStatus = new ArrayList <>();
         for (CustomerStatus status : CustomerStatus.values()) {
      	   customerStatus.add(status);
         }  	
         model.addAttribute("customerStatus", customerStatus);
         
         //TODO: changer le userRepository avec userService
         List<Users> users = new ArrayList <>();
         users = userRepository.findAll();
         model.addAttribute("users", users);
      	 	
        
    	
    	CustomersDto customerDto = customersService.findCustomerById(customerId);
    	model.addAttribute("customer", customerDto);
    	customerForm.setCustomerId(customerId);
    	customerForm.setFirstname(customerDto.getFirstname());
    	customerForm.setLastname(customerDto.getLastname());
    	customerForm.setCompany(customerDto.getCompany());
    	customerForm.setEmail(customerDto.getEmail());
    	customerForm.setOfficePhone(customerDto.getOfficePhone());
    	customerForm.setMobilePhone(customerDto.getMobilePhone());
    	customerForm.setCustomerStatus(customerDto.getCustomerStatus());
    	customerForm.setGuarantee(customerDto.getGuarantee());
    	customerForm.setUsers(customerDto.getUsers());
    	
    	
    	
    	return "updateCustomer";
    }
    
    @PreAuthorize("hasAuthority('SERVICE_CLIENT')")
    @PostMapping("/updateCustomer")
    public String checkCustomerUpdateInfo(
            @RequestParam("customerId") Integer customerId,
            @ModelAttribute("customerForm") @Validated CustomerForm customerForm,
            BindingResult bindingResult, Model model) {

        List<String> changes = new ArrayList<>();
        CustomersDto customerDto = customersService.findCustomerById(customerId);
        
        if (!bindingResult.hasErrors()) {
            // Perform comparison directly with form fields
            if (!Objects.equals(customerDto.getFirstname(), customerForm.getFirstname())) {
                changes.add("Firstname: " + customerDto.getFirstname() + " -> " + customerForm.getFirstname());
            }
            if (!Objects.equals(customerDto.getLastname(), customerForm.getLastname())) {
                changes.add("Lastname: " + customerDto.getLastname() + " -> " + customerForm.getLastname());
            }
            if (!Objects.equals(customerDto.getCompany(), customerForm.getCompany())) {
                changes.add("Company: " + customerDto.getCompany() + " -> " + customerForm.getCompany());
            }
            if (!Objects.equals(customerDto.getEmail(), customerForm.getEmail())) {
                changes.add("Email: " + customerDto.getEmail() + " -> " + customerForm.getEmail());
            }
            if (!Objects.equals(customerDto.getOfficePhone(), customerForm.getOfficePhone())) {
                changes.add("Office Phone: " + customerDto.getOfficePhone() + " -> " + customerForm.getOfficePhone());
            }
            if (!Objects.equals(customerDto.getMobilePhone(), customerForm.getMobilePhone())) {
                changes.add("Mobile Phone: " + customerDto.getMobilePhone() + " -> " + customerForm.getMobilePhone());
            }
            if (!Objects.equals(customerDto.getCustomerStatus(), customerForm.getCustomerStatus())) {
                changes.add("Customer Status: " + customerDto.getCustomerStatus() + " -> " + customerForm.getCustomerStatus());
            }
            if (!Objects.equals(customerDto.getGuarantee(), customerForm.getGuarantee())) {
                changes.add("Guarantee: " + customerDto.getGuarantee() + " -> " + customerForm.getGuarantee());
            }
            if (!Objects.equals(customerDto.getUsers(), customerForm.getUsers())) {
                changes.add("Users: " + customerDto.getUsers() + " -> " + customerForm.getUsers());
            }

            // Update the customer using form data directly
            customerDto.setFirstname(customerForm.getFirstname());
            customerDto.setLastname(customerForm.getLastname());
            customerDto.setCompany(customerForm.getCompany());
            customerDto.setEmail(customerForm.getEmail());
            customerDto.setOfficePhone(customerForm.getOfficePhone());
            customerDto.setMobilePhone(customerForm.getMobilePhone());
            customerDto.setCustomerStatus(customerForm.getCustomerStatus());
            customerDto.setGuarantee(customerForm.getGuarantee());
            customerDto.setUsers(customerForm.getUsers());

            customersService.create(customerDto);
            String changesMessage = "Update de Customer " + customerDto.getId() + String.join(", ", changes);
            userActionService.logUserAction("Client", changesMessage);

            model.addAttribute("message", "Customer updated successfully.");
            return "redirect:/listecustomers";
        } else {
            model.addAttribute("customers", customersService.showListeCustomers());
            return "updateCustomer";
        }
    }
    
    @PreAuthorize("hasAuthority('SERVICE_CLIENT')")
    @PostMapping("deleteCustomer")
    public String deleteCustomer(@RequestParam("customerId") Integer customerId, Model model) {
    	if (null == customerId) {
    		return "redirect:/listecustomers";
    	}

    	try {
    		customersService.deleteCustomerById(customerId);
    		model.addAttribute("message", "Customer deleted successfully.");
		} catch (Exception e) {
			model.addAttribute("message", "Error deleting Customer N°" + customerId + ": " + e.getMessage());
			}
    	userActionService.logUserAction("Client", "Suppression Customer N°" + customerId);
    	return "redirect:/listecustomers";

    }

}


