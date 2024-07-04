package company.cryo.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import company.cryo.crm.dto.CustomersDto;
import company.cryo.crm.dto.EstimateDto;
import company.cryo.crm.form.EstimateForm;
import company.cryo.crm.model.EstimateStatus;
import company.cryo.crm.repository.UsersRepository;
import company.cryo.crm.service.CustomersService;
import company.cryo.crm.service.EstimateService;
import company.cryo.crm.service.MapperService;
import company.cryo.crm.service.UserActionService;
import company.cryo.crm.config.MonUserDetails;

@Controller
public class EstimateController {

	 private final MapperService mapperService;
	    private final EstimateService estimateService;
	    private final CustomersService customersService;
	    private final UserActionService userActionService;
	    private final UsersRepository userRepository;
	    
	    @Autowired
	    public EstimateController(MapperService mapperService, 
	                              EstimateService estimateService,
	                              CustomersService customersService,
	                              UserActionService userActionService,
	                              UsersRepository userRepository) {
	        this.mapperService = mapperService;
	        this.estimateService = estimateService;
	        this.customersService = customersService;
	        this.userActionService = userActionService;
	        this.userRepository = userRepository;
	    }

    private List<CustomersDto> getCustomers() {
        return this.customersService.showListeCustomers();
    }


    @PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @GetMapping("/createEstimate")
    public String showCreationEstimateForm(Model model, @ModelAttribute("estimateForm") EstimateForm form) {
        model.addAttribute("estimateForm", new EstimateForm());
        model.addAttribute("customers", getCustomers());
        model.addAttribute("userId", 1);
        
        
        MonUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            form.setUserId(userDetails.getId());
        } else {
        	model.addAttribute("errorMessage", "ERROR recuperation ID userConnect en methodGET createEstimate");
        	return "redirect:/errorPage";
        } 
        return "createEstimate";
    }

    

	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @PostMapping("/createEstimate")
    public String createEstimate(
            @ModelAttribute("estimateForm") @Validated EstimateForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", getCustomers());  // Add customers to model on error
            return "createEstimate";
        }
        
        MonUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            form.setUserId(userDetails.getId());
        } else {
        	model.addAttribute("errorMessage", "ERROR recuperation ID userConnect en methodGET createEstimate");
        	return "redirect:/errorPage";
        }
        
        EstimateDto estimateDto = mapperService.mapToEstimateDto(form);
        estimateService.createEstimate(estimateDto);
        userActionService.logUserAction("Devis", "Creacion de Devis "+ estimateDto.getEstimateLabel());
        model.addAttribute("message", "Estimate created successfully.");
        return "redirect:/listEstimates";
    }

    @GetMapping("/updateEstimate")
    public String showUpdateEstimateForm(@RequestParam(name = "id") Integer estimateId, @ModelAttribute("estimateForm") EstimateForm form, Model model) {
        if (estimateId != null) {
            EstimateDto existingEstimate = estimateService.getEstimatesById(estimateId).orElse(null);
            if (existingEstimate != null) {
                model.addAttribute("estimateForm", existingEstimate);
                model.addAttribute("customers", getCustomers());
            }
        } else {
            return "redirect:/createEstimate";
        }
        return "createEstimate";
    }

    @PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @PostMapping("/updateEstimate")
    public String updateEstimate(
            @ModelAttribute("estimateForm") @Validated EstimateForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", getCustomers());
            return "createEstimate";
        }

        List<String> changes = new ArrayList<>();
        EstimateDto currentEstimateDto = estimateService.getEstimatesById(form.getId()).orElse(null);
        
        // Perform comparison directly with form fields
        if (!Objects.equals(currentEstimateDto.getAverageDailyRate(), form.getAverageDailyRate())) {
            changes.add("Average Daily Rate: " + currentEstimateDto.getAverageDailyRate() + " -> " + form.getAverageDailyRate());
        }
        if (!Objects.equals(currentEstimateDto.getNumberOfDays(), form.getNumberOfDays())) {
            changes.add("Number of Days: " + currentEstimateDto.getNumberOfDays() + " -> " + form.getNumberOfDays());
        }
        if (!Objects.equals(currentEstimateDto.getTva(), form.getTva())) {
            changes.add("TVA: " + currentEstimateDto.getTva() + " -> " + form.getTva());
        }
        if (!Objects.equals(currentEstimateDto.getTransfered(), form.getTransfered())) {
            changes.add("Transfered: " + currentEstimateDto.getTransfered() + " -> " + form.getTransfered());
        }
        if (!Objects.equals(currentEstimateDto.getEstimateLabel(), form.getEstimateLabel())) {
            changes.add("Estimate Label: " + currentEstimateDto.getEstimateLabel() + " -> " + form.getEstimateLabel());
        }
        if (currentEstimateDto.getEstimateStatus() != form.getEstimateStatus()) {
            changes.add("Estimate Status: " + currentEstimateDto.getEstimateStatus() + " -> " + form.getEstimateStatus());
        }
        if (!Objects.equals(currentEstimateDto.getEstimateComment(), form.getEstimateComment())) {
            changes.add("Estimate Comment: " + currentEstimateDto.getEstimateComment() + " -> " + form.getEstimateComment());
        }
        
        
        // Update the estimate using form data directly
        EstimateDto updatedEstimateDto = new EstimateDto();
        updatedEstimateDto.setId(form.getId());
        updatedEstimateDto.setAverageDailyRate(form.getAverageDailyRate());
        updatedEstimateDto.setNumberOfDays(form.getNumberOfDays());
        updatedEstimateDto.setTva(form.getTva());
        updatedEstimateDto.setTransfered(form.getTransfered());
        updatedEstimateDto.setEstimateLabel(form.getEstimateLabel());
        updatedEstimateDto.setEstimateStatus(form.getEstimateStatus());
        updatedEstimateDto.setEstimateComment(form.getEstimateComment());
        
        estimateService.updateEstimate(updatedEstimateDto);
        
        String changesMessage = "Update de Devis " + updatedEstimateDto.getId() + String.join(", ", changes);
        userActionService.logUserAction("Devis", changesMessage);
        
        model.addAttribute("message", "Estimate updated successfully.");
        return "redirect:/listEstimates";
    }
    @PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @GetMapping("/viewEstimate")
    public String viewEstimate(@RequestParam("id") Integer estimateId, Model model) {
        EstimateDto estimate = estimateService.getEstimatesById(estimateId).orElse(null);
        if (estimate != null) {
            model.addAttribute("estimate", estimate);
        } else {
            model.addAttribute("message", "Estimate not found.");
            return "redirect:/listEstimates";
        }
        return "viewEstimate";
    }

    @PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @GetMapping("/listEstimates")
    public String listEstimates(@RequestParam(required = false) EstimateStatus status,
                                @RequestParam(required = false) String label,
                                @RequestParam(required = false) String reference,
                                @RequestParam(required = false) String customerName,
                                Model model) {
        model.addAttribute("estimateStatus", EstimateStatus.values());
        model.addAttribute("status", status);
        model.addAttribute("label", label);
        model.addAttribute("reference", reference);
        model.addAttribute("customerName", customerName);

        if (status != null || label != null ||reference !=null || customerName != null) {
            model.addAttribute("estimates", estimateService.getEstimatesByFilters(status, label, reference, customerName));
        } else {
            model.addAttribute("estimates", estimateService.getAllEstimates());
        }
        
        return "listEstimates";
    }
    
    @PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @PostMapping("/deleteEstimate")
    public String deleteEstimate(@RequestParam("estimateId") Integer estimateId, Model model) {
        if (estimateId != null) {
            try {
                estimateService.deleteEstimate(estimateId);
                
                model.addAttribute("message", "Estimate deleted successfully.");
            } catch (Exception e) {
            	System.out.println(e.getStackTrace() + e.getMessage());
                model.addAttribute("message", "Error deleting Devis N°" + estimateId + ": " + e.getMessage());
            }
            userActionService.logUserAction("Devis", "Suppression Devis N°" + estimateId);
        }
        return "redirect:/listEstimates";
    }
    private MonUserDetails getCurrentUserDetails() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MonUserDetails) {
            return (MonUserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
