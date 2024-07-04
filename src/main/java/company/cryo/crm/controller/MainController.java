package company.cryo.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String welcome() {
        return "redirect:/listecustomers";
    }

    @GetMapping("/errorPage")
    public String error(Model model) {
        return "errorPage";
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
        
        @ExceptionHandler(Exception.class)
        public String handleException(Exception e, Model model) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
    }
}
