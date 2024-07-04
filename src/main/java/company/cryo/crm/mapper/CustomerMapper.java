package company.cryo.crm.mapper;


import company.cryo.crm.dto.CustomersDto;
import company.cryo.crm.model.Customers;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomersDto toDto(Customers model){

        if (null == model) {
            return null;
        }

        CustomersDto dto = new CustomersDto();
        dto.setId(model.getId());
        dto.setCompany(model.getCompany());
        dto.setEmail(model.getEmail());
        dto.setCustomerStatus(model.getCustomerStatus());
        dto.setCustomerComment(model.getCustomerComment());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setEstimates(model.getEstimates());
        dto.setOrders(model.getOrders());
        dto.setMobilePhone(model.getMobilePhone());
        dto.setOfficePhone(model.getOfficePhone());
        dto.setGuarantee(model.getGuarantee());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUsers(model.getUsers());
        dto.setUpdatedAt(model.getUpdatedAt());
        dto.setCustomerStatus(model.getCustomerStatus());
        return dto;
    }

    public Customers toModel(CustomersDto dto){

        if (null == dto) {
            return null;
        }

        Customers model = new Customers();
        model.setId(dto.getId());
        model.setCompany(dto.getCompany());
        model.setEmail(dto.getEmail());
        model.setCustomerStatus(dto.getCustomerStatus());
        model.setCustomerComment(dto.getCustomerComment());
        model.setFirstname(dto.getFirstname());
        model.setLastname(dto.getLastname());
        model.setEstimates(dto.getEstimates());
        model.setOrders(dto.getOrders());
        model.setMobilePhone(dto.getMobilePhone());
        model.setOfficePhone(dto.getOfficePhone());
        model.setGuarantee(dto.getGuarantee());
        model.setCreatedAt(dto.getCreatedAt());
        model.setUsers(dto.getUsers());
        model.setUpdatedAt(dto.getUpdatedAt());
        model.setCustomerStatus(dto.getCustomerStatus());
        return model;
    }


}
