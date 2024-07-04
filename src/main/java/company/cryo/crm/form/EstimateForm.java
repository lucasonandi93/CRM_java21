package company.cryo.crm.form;

import java.sql.Timestamp;

import company.cryo.crm.model.EstimateStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EstimateForm {

    private Integer id;  

    @NotNull
    @Size(min=1, max=100)
    private String estimateLabel;

    @NotNull
    private Integer numberOfDays;

    @NotNull
    private Double averageDailyRate;

    @NotNull
    private Double tva;

    @NotNull
    private EstimateStatus estimateStatus;

    private Boolean transfered;

    private Integer userId;

    private Integer customerId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
    private String estimateComment;
    

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstimateComment() {
		return estimateComment;
	}

	public void setEstimateComment(String estimateComment) {
		this.estimateComment = estimateComment;
	}

	public String getEstimateLabel() {
        return estimateLabel;
    }

    public void setEstimateLabel(String estimateLabel) {
        this.estimateLabel = estimateLabel;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getAverageDailyRate() {
        return averageDailyRate;
    }

    public void setAverageDailyRate(Double averageDailyRate) {
        this.averageDailyRate = averageDailyRate;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public EstimateStatus getEstimateStatus() {
        return estimateStatus;
    }

    public void setEstimateStatus(EstimateStatus estimateStatus) {
        this.estimateStatus = estimateStatus;
    }

    public Boolean getTransfered() {
        return transfered;
    }

    public void setTransfered(Boolean transfered) {
        this.transfered = transfered;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

	@Override
	public String toString() {
		return "EstimateForm [id=" + id + ", estimateLabel=" + estimateLabel + ", numberOfDays=" + numberOfDays
				+ ", averageDailyRate=" + averageDailyRate + ", tva=" + tva + ", estimateStatus=" + estimateStatus
				+ ", transfered=" + transfered + ", userId=" + userId + ", customerId=" + customerId + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", estimateComment=" + estimateComment + "]";
	}
    
}
