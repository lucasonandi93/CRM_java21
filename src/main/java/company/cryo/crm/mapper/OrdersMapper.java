package company.cryo.crm.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import company.cryo.crm.dto.OrdersDto;
import company.cryo.crm.model.Orders;

@Component
public class OrdersMapper {

	@Autowired
	private CustomerMapper customersMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private EstimateMapper estimateMapper;
	
	public Orders toModel(OrdersDto dto) {
		Orders model = new Orders();
		model.setId(dto.getId());
		model.setOrderLabel(dto.getOrderLabel());
		model.setOrderType(dto.getOrderType());
		model.setOrderStatus(dto.getOrderStatus());
		model.setOrderComment(dto.getOrderComment());
		model.setCustomers(customersMapper.toModel(dto.getCustomer()));
		model.setEstimates(estimateMapper.toModel(dto.getEstimate()));
		model.setUsers(userMapper.toModel(dto.getUser()));
		model.setCreatedAt(dto.getCreatedAt());
		model.setUpdatedAt(dto.getUpdatedAt());
		return model;
	}
	
	public OrdersDto toDto(Orders model) {
		if (null == model) {
			return null;
		}
		OrdersDto orderDto = new OrdersDto();
		System.out.println("test toDto"+customersMapper.toDto(model.getCustomers()).toString());
		orderDto.setId(model.getId());
		orderDto.setOrderLabel(model.getOrderLabel());
		orderDto.setOrderStatus(model.getOrderStatus());
		orderDto.setOrderType(model.getOrderType());
		orderDto.setOrderComment(model.getOrderComment());
		orderDto.setEstimate(estimateMapper.toDto(model.getEstimates()));
		orderDto.setCustomer(customersMapper.toDto(model.getCustomers()));
		orderDto.setUser(userMapper.toDto(model.getUsers()));
		orderDto.setCreatedAt(model.getCreatedAt());
		orderDto.setUpdatedAt(model.getUpdatedAt());
		return orderDto;
	}

	public List<Orders> toModel(List<OrdersDto> orders) {
		if (null == orders) {
			return null;
		}
		return orders.stream().map(
					(dto) -> this.toModel(dto)
				)
				.collect(Collectors.toList());
	}

	public List<OrdersDto> toDto(List<Orders> orders) {
		if (null == orders) {
			return null;
		}
		return orders.stream()
				.map(this::toDto).collect(
						Collectors.toList());
	}
	
		
}
