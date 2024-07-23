package company.cryo.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.cryo.crm.dto.CustomersDto;
import company.cryo.crm.dto.EstimateDto;
import company.cryo.crm.dto.UsersDto;
import company.cryo.crm.form.EstimateForm;
import company.cryo.crm.model.Customers;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.CustomersRepository;
import company.cryo.crm.repository.UsersRepository;

@Service
public class MapperService {

    private final UsersRepository userRepository;
    private final CustomersRepository customerRepository;


    @Autowired
    public MapperService(UsersRepository userRepository, CustomersRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public EstimateDto mapToEstimateDto(EstimateForm estimateForm) {
        EstimateDto estimateDto = new EstimateDto();
        estimateDto.setId(estimateForm.getId());
        estimateDto.setEstimateLabel(estimateForm.getEstimateLabel());
        estimateDto.setNumberOfDays(estimateForm.getNumberOfDays());
        estimateDto.setAverageDailyRate(estimateForm.getAverageDailyRate());
        estimateDto.setTva(estimateForm.getTva());
        estimateDto.setEstimateStatus(estimateForm.getEstimateStatus());
        estimateDto.setTransfered(estimateForm.getTransfered());
        estimateDto.setEstimateComment(estimateForm.getEstimateComment());
        estimateDto.setCreatedAt(estimateForm.getCreatedAt());
        estimateDto.setUpdatedAt(estimateForm.getUpdatedAt());
        
        Customers customer = customerRepository.findById(estimateForm.getCustomerId()).orElseThrow(() -> new RuntimeException("customer not found"));
        CustomersDto customerDto = toCustomersDto(customer);

        estimateDto.setCustomer(customerDto);

        Users user = userRepository.findById(estimateForm.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        UsersDto userDto = toUsersDto(user);

        estimateDto.setUser(userDto);

        return estimateDto;
    }

    public UsersDto toUsersDto(Users user) {
        UsersDto userDto = new UsersDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setGrantName(user.getGrantName());
        userDto.setActive(user.getActive());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userDto;
    }
    
    public CustomersDto toCustomersDto(Customers model){

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
}
