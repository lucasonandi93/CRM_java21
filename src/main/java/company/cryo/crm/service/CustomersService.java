package company.cryo.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import company.cryo.crm.dto.CustomersDto;
import company.cryo.crm.mapper.CustomerMapper;
import company.cryo.crm.model.CustomerStatus;
import company.cryo.crm.model.Customers;
import company.cryo.crm.repository.CustomersRepository;

@Service
@Transactional
public class CustomersService {

    @Autowired
    CustomersRepository customersRepository;
    
    @Autowired
    CustomerMapper customerMapper;

    public void create(CustomersDto dto) {
	    Customers customer = customerMapper.toModel(dto);
	    customersRepository.save(customer);
	
}

    public List<CustomersDto> showListeCustomers(){
        List<Customers> customersList = new ArrayList<>();
        customersList.addAll(customersRepository.findAll());
        return customersList.stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    public List<CustomersDto> getCustumersByFilter(CustomerStatus customerStatus, String company) {
        System.out.println("-----------------------------TEST customerStatus------------");
        System.out.println(customerStatus);
        System.out.println("-----------------------------TEST customerStatus------------");
        System.out.println(company);
        List<Customers> customers = customersRepository.findByStatusAndByCompany(customerStatus, company);
        return customers.stream().map(customerMapper::toDto).collect(Collectors.toList());
    }
    public CustomersDto findCustomerById(Integer customerId) {
    	Optional <Customers> customer = customersRepository.findById(customerId);
    	CustomersDto customerDto = customerMapper.toDto(customer.get());
    	return customerDto;
    
    }
    
    public void deleteCustomerById(Integer customerId) {
    	customersRepository.deleteById(customerId);
    }
}
