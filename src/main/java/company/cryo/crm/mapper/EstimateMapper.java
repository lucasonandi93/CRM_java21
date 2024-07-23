package company.cryo.crm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import company.cryo.crm.dto.EstimateDto;
import company.cryo.crm.model.Estimates;
import jakarta.validation.Valid;

@Component
public class EstimateMapper {
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	CustomerMapper customerMapper;

    public Estimates toModel(@Valid EstimateDto estimateDto) {
        if (estimateDto == null) {
            return null;
        }

        Estimates model = new Estimates();
        model.setId(estimateDto.getId());
        model.setEstimateLabel(estimateDto.getEstimateLabel());
        model.setNumberOfDays(estimateDto.getNumberOfDays());
        model.setAverageDailyRate(estimateDto.getAverageDailyRate());
        model.setTva(estimateDto.getTva());
        model.setEstimateStatus(estimateDto.getEstimateStatus());
        model.setEstimateComment(estimateDto.getEstimateComment());
        model.setTransfered(estimateDto.getTransfered());
        model.setCreatedAt(estimateDto.getCreatedAt());
        model.setUpdatedAt(estimateDto.getUpdatedAt());
        model.setCustomers(customerMapper.toModel(estimateDto.getCustomer()));
        model.setUsers(userMapper.toModel(estimateDto.getUser()));
        return model;
    }

    public EstimateDto toDto(Estimates model) {
        if (model == null) {
            return null;
        }

        EstimateDto dto = new EstimateDto();
        dto.setId(model.getId());
        dto.setEstimateLabel(model.getEstimateLabel());
        dto.setNumberOfDays(model.getNumberOfDays());
        dto.setAverageDailyRate(model.getAverageDailyRate());
        dto.setTva(model.getTva());
        dto.setEstimateStatus(model.getEstimateStatus());
        dto.setEstimateComment(model.getEstimateComment());
        dto.setTransfered(model.getTransfered());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        dto.setCustomer(customerMapper.toDto(model.getCustomers()));
        dto.setUser(userMapper.toDto(model.getUsers()));
        return dto;
    }
}
