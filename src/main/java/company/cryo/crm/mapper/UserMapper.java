package company.cryo.crm.mapper;

import org.springframework.stereotype.Component;

import company.cryo.crm.dto.UsersDto;

import company.cryo.crm.model.Users;

@Component
public class UserMapper {

	public UsersDto toDto(Users model) {
		if (model == null) {
			return null;
		}

		UsersDto dto = new UsersDto();
		dto.setId(model.getId());
		dto.setFirstname(model.getFirstname());
		dto.setLastname(model.getLastname());
		dto.setEmail(model.getEmail());
		dto.setUserPassword(model.getUserPassword());
		dto.setGrantName(model.getGrantName());
		dto.setActive(model.getActive());
		dto.setCreatedAt(model.getCreatedAt());
		dto.setUpdatedAt(model.getUpdatedAt());

		// dto.setCustomers(model.getCustomers());
		// dto.setOrders(model.getOrders());
		// dto.setEstimates(model.getEstimates());

		return dto;
	}

	public Users toModel(UsersDto dto) {
		if (dto == null) {
			return null;
		}

		Users model = new Users();
		model.setId(dto.getId());
		model.setFirstname(dto.getFirstname());
		model.setLastname(dto.getLastname());
		model.setEmail(dto.getEmail());
		model.setUserPassword(dto.getUserPassword());
		model.setGrantName(dto.getGrantName());
		model.setActive(dto.getActive());
		model.setCreatedAt(dto.getCreatedAt());
		model.setUpdatedAt(dto.getUpdatedAt());

		// model.setCustomers(dto.getCustomers());
		// model.setOrders(dto.getOrders());
		// model.setEstimates(dto.getEstimates());

		return model;
	}
}
