package company.cryo.crm.dto;

import java.sql.Timestamp;

import company.cryo.crm.model.EstimateStatus;

public class EstimateDto {

	 private Integer id;
	    private String estimateLabel;
	    private Integer numberOfDays;
	    private Double averageDailyRate;
	    private Double tva;
	    private EstimateStatus estimateStatus;
	    private String estimateComment;
	    private Boolean transfered;
	    private UsersDto user;
	    private CustomersDto customer;
	    private Timestamp createdAt;
	    private Timestamp updatedAt;
	    
    
    // Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getEstimateComment() {
		return estimateComment;
	}
	public void setEstimateComment(String estimateComment) {
		this.estimateComment = estimateComment;
	}
	public Boolean getTransfered() {
		return transfered;
	}
	public void setTransfered(Boolean transferred) {
		this.transfered = transferred;
	}
	public UsersDto getUser() {
		return user;
	}
	public void setUser(UsersDto user) {
		this.user = user;
	}
	public CustomersDto getCustomer() {
		return customer;
	}
	public void setCustomer(CustomersDto customer) {
		this.customer = customer;
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
	public Double getMontantHT() {
		return getAverageDailyRate() * getNumberOfDays();
	}
	public Double getMontantTTC() {
		return getMontantHT() * (1 + (getTva()/100));
	}
	@Override
	public String toString() {
		return "EstimateDto [id=" + id + ", estimateLabel=" + estimateLabel + ", numberOfDays=" + numberOfDays
				+ ", averageDailyRate=" + averageDailyRate + ", tva=" + tva + ", estimateStatus=" + estimateStatus
				+ ", estimateComment=" + estimateComment + ", transfered=" + transfered + ", user=" + user
				+ ", customer=" + customer + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	

}
