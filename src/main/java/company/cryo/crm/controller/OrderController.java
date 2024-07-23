package company.cryo.crm.controller;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import company.cryo.crm.dto.OrdersDto;
import company.cryo.crm.form.OrderForm;
import company.cryo.crm.mapper.OrdersMapper;
import company.cryo.crm.model.Customers;
import company.cryo.crm.model.EstimateStatus;
import company.cryo.crm.model.Estimates;
import company.cryo.crm.model.OrderStatus;
import company.cryo.crm.model.Orders;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.EstimateRepository;
import company.cryo.crm.repository.OrdersRepository;
import company.cryo.crm.service.OrderService;
import company.cryo.crm.service.UserActionService;



/**
 * Controller to create, list and delete orders
 */
@Controller
public class OrderController {

	@Autowired
	private EstimateRepository estimateRepository;
	
	@Autowired
	private OrdersRepository orderRepository;
	
	@Autowired
	private OrderService orderService; 
	
	@Autowired
	private OrdersMapper ordersMapper;

    
	
	

	@Autowired
	private UserActionService userActionService;
	
	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
	@GetMapping("/createOrder")
	public String showCreationOrderForm(
			@ModelAttribute("orderForm") OrderForm form,
			Model model) {
		try {
			// TODO Récupérer les devis validés de l'utilisateur connecté
			List<Estimates> listEstimates = estimateRepository.findByEstimateStatus(EstimateStatus.VALIDE);
			
			model.addAttribute("listOrderStatus", OrderStatus.values());
	        model.addAttribute("listEstimates", listEstimates);
		} catch (Exception e) {
			model.addAttribute("message", "ERROR: "+e.getMessage());
			return "redirect:/errorPage";
		}
		
		return "createOrder";
	}
	
	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
	@PostMapping("/createOrder")
	public String createOrder(
			@ModelAttribute("orderForm") @Validated 
				OrderForm form,
				BindingResult bindingResult,
				Model model
			) {
		if (! bindingResult.hasErrors()) {
			
			Orders order = new Orders();
			order.setOrderLabel(form.getOrderLabel());
			order.setOrderStatus(form.getOrderStatus());
			order.setOrderType(form.getOrderType());
			order.setOrderComment(form.getOrderComment());

			Estimates estimate = estimateRepository.findById(form.getEstimatesId()).orElse(null);
			
			// TODO : vérifier que le user_id du devis est bien celui de l'utilisateur connecté
			if (null == estimate || estimate.getEstimateStatus() != EstimateStatus.VALIDE) {
				model.addAttribute("errorMessage", "Erreur : Devis invalide");
				return showCreationOrderForm(form, model);
			}
			
			Customers customer = estimate.getCustomers();
			Users user = estimate.getUsers();
			order.setCustomers(customer);
			order.setEstimates(estimate);
			order.setUsers(user);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			order.setCreatedAt(timestamp);
			order.setUpdatedAt(timestamp);

			orderRepository.save(order);
			userActionService.logUserAction("Order", "Creation de Order "+ order.getOrderLabel());
			estimate.setEstimateStatus(EstimateStatus.ARCHIVE);
			estimateRepository.save(estimate);
			userActionService.logUserAction("Order", "Creation de Order et Changement de status Devis N°"+estimate.getId()+" a ARCHIVE");

			model.addAttribute("message", "La commande a été créée.");
		} else {
			return showCreationOrderForm(form, model);
		}
		return listOrder(null, null, null, null, model);
	}
	
	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
	@GetMapping("/listOrders")
	public String listOrder(@RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) String reference,
            @RequestParam(required = false) String customerName,Model model) {
		
		// TODO Afficher les commandes en fonction de l'utilisateur connecté
		try {
		model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("status", status);
        model.addAttribute("label", label);
        model.addAttribute("reference", reference);
        model.addAttribute("customerName", customerName);
        
        if (status != null || label != null ||reference !=null || customerName != null) {
            model.addAttribute("ordersList", orderService.getOrderByFilters(status, label, reference, customerName));
        } else {
            model.addAttribute("ordersList", orderService.getAllOrders());
        }
        OrdersDto orderDto = orderService.getAllOrders().getFirst();
        System.out.println("allOrders= " + orderDto.toString());
		}catch (Exception e){
			model.addAttribute("errorMessage", "Error al listar las órdenes: " + e.getMessage());
	        return "errorPage"; 
		}
        return "listOrders";
	}

	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
	@GetMapping("/showOrder")
	public String showOrder(@RequestParam(name = "id")Integer orderId, Model model) {
		if (null == orderId) {
			return "redirect:/listOrders";
		}
		Orders order = orderRepository.findById(orderId).orElse(null);
        if (null == order) {
            return "redirect:/listOrders";
        }
        model.addAttribute("order", order);
		return "showOrder";
	}
	
	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @GetMapping("/updateOrder")
    public String showUpdateOrderForm(
    		@RequestParam(name = "id", required = false) Integer orderId, 
			@ModelAttribute("orderForm") OrderForm form,
    		Model model
    		) {
    	if (null == orderId) return "redirect:/listOrders";
    	
		Orders existingOrder = orderRepository.findById(orderId).orElse(null);
		if (null == existingOrder) return "redirect:/listOrders";

		OrdersDto existingOrderDto = ordersMapper.toDto(existingOrder);
		model.addAttribute("orderForm", existingOrderDto);

		model.addAttribute("listOrderStatus", OrderStatus.values());
         
        return "createOrder";
    }
    
	@PreAuthorize("hasAuthority('GESTIONNAIRE_DES_VENTES')")
    @PostMapping("/updateOrder")
    public String updateOrder(
            @ModelAttribute("orderForm") @Validated OrderForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("errorMessage", "Erreur lors de la modification.");
            return showUpdateOrderForm(form.getId(), form, model);
        }
        
        List<String> changes = new ArrayList<>();
        Orders order = orderRepository.findById(form.getId()).orElse(null);
        if (null == order) {
        	model.addAttribute("errorMessage", "Erreur : La commande n'a pas été trouvée.");
            return showUpdateOrderForm(form.getId(), form, model);
        }
        if (!Objects.equals(order.getOrderLabel(), form.getOrderLabel())) {
            changes.add("Order Label: " + order.getOrderLabel() + " -> " + form.getOrderLabel());
            order.setOrderLabel(form.getOrderLabel());
        }
        if (!Objects.equals(order.getOrderStatus(), form.getOrderStatus())) {
            changes.add("Order Status: " + order.getOrderStatus() + " -> " + form.getOrderStatus());
            order.setOrderStatus(form.getOrderStatus());
        }
        if (!Objects.equals(order.getOrderType(), form.getOrderType())) {
            changes.add("Order Type: " + order.getOrderType() + " -> " + form.getOrderType());
            order.setOrderType(form.getOrderType());
        }
        if (!Objects.equals(order.getOrderComment(), form.getOrderComment())) {
            changes.add("Order Comment: " + order.getOrderComment() + " -> " + form.getOrderComment());
            order.setOrderComment(form.getOrderComment());
        }
        order.setOrderLabel(form.getOrderLabel());
        order.setOrderStatus(form.getOrderStatus());
        order.setOrderType(form.getOrderType());
        order.setOrderComment(form.getOrderComment());
        
        orderRepository.save(order);
        String changesMessage = "Update de Order " + order.getId() + String.join(", ", changes);
        userActionService.logUserAction("Order", changesMessage);
        
        model.addAttribute("message", "Order updated successfully.");
        return "redirect:/listOrders";
    }
	
}