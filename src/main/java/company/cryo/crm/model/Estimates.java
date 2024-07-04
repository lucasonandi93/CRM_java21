package company.cryo.crm.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Estimates {

    @Id
    @Column(name = "estimate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estimate_label")
    private String estimateLabel;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "average_daily_rate", columnDefinition = "NUMERIC")
    private Double averageDailyRate;

    @Column(columnDefinition = "NUMERIC")
    private Double tva;

    @Column(name = "estimate_status")
    @Enumerated(EnumType.STRING)
    private EstimateStatus estimateStatus;
    
    @Column(name = "estimate_comment")
    private String estimateComment;

    private Boolean transfered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToOne(mappedBy="estimates")
    private Orders orders;
    
    public Estimates() {
    	super();
    }

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

    public void setTransfered(Boolean transfered) {
        this.transfered = transfered;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
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

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

	@Override
	public String toString() {
		return "Estimates [id=" + id + ", estimateLabel=" + estimateLabel + ", numberOfDays=" + numberOfDays
				+ ", averageDailyRate=" + averageDailyRate + ", tva=" + tva + ", estimateStatus=" + estimateStatus
				+ ", estimateComment=" + estimateComment + ", transfered=" + transfered + ", users=" + users
				+ ", customers=" + customers + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", orders="
				+ orders + "]";
	}
    
   
}
