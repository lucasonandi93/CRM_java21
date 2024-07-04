package company.cryo.crm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.cryo.crm.dto.OrdersDto;
import company.cryo.crm.form.OrderForm;
import company.cryo.crm.mapper.OrdersMapper;
import company.cryo.crm.model.OrderStatus;
import company.cryo.crm.model.Orders;
import company.cryo.crm.repository.OrdersRepository;
import jakarta.validation.Valid;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private OrdersMapper orderMapper;

    public List<OrdersDto> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<OrdersDto> getOrdersById(Integer id) {
        Optional<Orders> order = orderRepository.findById(id);
        return order.map(orderMapper::toDto);
    }

//    public OrdersDto createOrder(@Valid OrderForm OrderDto) {
//        Orders order = orderMapper.toModel(OrderDto);
//        Orders savedOrder = orderRepository.save(order);
//        return orderMapper.toDto(savedOrder);
//    }
    public OrdersDto updateOrder(@Valid OrderForm orderForm) {
        
        Optional<Orders> optionalOrder = orderRepository.findById(orderForm.getId());
        if (optionalOrder.isPresent()) {
            Orders existingOrder = optionalOrder.get();
            existingOrder.setOrderLabel(orderForm.getOrderLabel());
            existingOrder.setOrderStatus(orderForm.getOrderStatus());
            Orders savedOrder = orderRepository.save(existingOrder);
            return orderMapper.toDto(savedOrder);
        } else {
            throw new IllegalArgumentException("Order with id " + orderForm.getId() + " not found");
        }
    }

	public List<OrdersDto> getOrderByFilters(OrderStatus status, String label, String reference, String customerName) {
		 List<Orders> orders = orderRepository.findByFilters(status, label, reference, customerName);
	        return orders.stream()
	                .map(orderMapper::toDto)
	                .collect(Collectors.toList());
	    }

	
}
