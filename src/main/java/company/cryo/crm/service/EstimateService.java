package company.cryo.crm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import company.cryo.crm.dto.EstimateDto;
import company.cryo.crm.form.EstimateForm;
import company.cryo.crm.mapper.CustomerMapper;
import company.cryo.crm.mapper.EstimateMapper;
import company.cryo.crm.mapper.UserMapper;
import company.cryo.crm.model.Customers;
import company.cryo.crm.model.EstimateStatus;
import company.cryo.crm.model.Estimates;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.EstimateRepository;
import jakarta.validation.Valid;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private EstimateMapper estimateMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public List<EstimateDto> getAllEstimates() {
        List<Estimates> estimates = estimateRepository.findAll();
        return estimates.stream()
                .map(estimateMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<EstimateDto> getEstimatesById(Integer id) {
        Optional<Estimates> estimate = estimateRepository.findById(id);
        return estimate.map(estimateMapper::toDto);
    }
    
    @Transactional
    public EstimateDto createEstimate(@Valid EstimateDto EstimateDto) {
        Estimates estimate = estimateMapper.toModel(EstimateDto);
        System.out.println("estimate en EstimateService= "+ estimate.toString());
        Estimates savedEstimate = estimateRepository.save(estimate);
        return estimateMapper.toDto(savedEstimate);
    }
    
    @Transactional
    public EstimateDto updateEstimate(@Valid EstimateDto estimateDto) {
        Optional<Estimates> optionalEstimate = estimateRepository.findById(estimateDto.getId());
        if (optionalEstimate.isPresent()) {
            Estimates existingEstimate = optionalEstimate.get();
            existingEstimate.setEstimateLabel(estimateDto.getEstimateLabel());
            existingEstimate.setNumberOfDays(estimateDto.getNumberOfDays());
            existingEstimate.setAverageDailyRate(estimateDto.getAverageDailyRate());
            existingEstimate.setTva(estimateDto.getTva());
            existingEstimate.setEstimateStatus(estimateDto.getEstimateStatus());
            existingEstimate.setEstimateComment(estimateDto.getEstimateComment());
            existingEstimate.setTransfered(estimateDto.getTransfered());

            if (estimateDto.getUser() != null) {
                Users user = userMapper.toModel(estimateDto.getUser());
                existingEstimate.setUsers(user);
            }

            if (estimateDto.getCustomer() != null) {
                Customers customer = customerMapper.toModel(estimateDto.getCustomer());
                existingEstimate.setCustomers(customer);
            }

            Estimates savedEstimate = estimateRepository.save(existingEstimate);
            return estimateMapper.toDto(savedEstimate);
        } else {
            throw new IllegalArgumentException("Estimate with id " + estimateDto.getId() + " not found");
        }
    }

    
    @Transactional
    public void deleteEstimate(Integer id) {
        estimateRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<EstimateDto> getEstimatesByFilters(EstimateStatus status, String label, String reference, String customerName) {
        List<Estimates> estimates = estimateRepository.findByFilters(status, label, reference, customerName);
        return estimates.stream()
                .map(estimateMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al acceder a la base de datos");
    }
	
}
