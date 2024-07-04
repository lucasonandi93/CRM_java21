package company.cryo.crm.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import company.cryo.crm.config.MonUserDetails;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "home";
	}

	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}

	@GetMapping("/unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}
	
	@GetMapping("/monCompte")
	public String monCompte(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MonUserDetails userDetails = (MonUserDetails) authentication.getPrincipal();
		model.addAttribute("userId", userDetails.getId());
        model.addAttribute("userInfos", userDetails.getInfos());
		return "monCompte";
	}
	
}
