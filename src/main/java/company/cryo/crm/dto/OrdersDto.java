package company.cryo.crm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import company.cryo.crm.model.OrderStatus;


public class OrdersDto implements Serializable {

	private static final long serialVersionUID = 4256190920186732280L;

	private Integer id;

	private String orderLabel;

	private String orderType;

	private OrderStatus orderStatus;

	private String orderComment;

	private CustomersDto customer;

	private EstimateDto estimate;

	private UsersDto user;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderLabel() {
		return orderLabel;
	}

	public void setOrderLabel(String orderLabel) {
		this.orderLabel = orderLabel;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderComment() {
		return orderComment;
	}

	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
	}

	public CustomersDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomersDto customer) {
		this.customer = customer;
	}

	public EstimateDto getEstimate() {
		return estimate;
	}

	public void setEstimate(EstimateDto estimate) {
		this.estimate = estimate;
	}

	public UsersDto getUser() {
		return user;
	}

	public void setUser(UsersDto user) {
		this.user = user;
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
		return "OrdersDto [id=" + id + ", orderLabel=" + orderLabel + ", orderType=" + orderType + ", orderStatus="
				+ orderStatus + ", orderComment=" + orderComment + ", customer=" + customer + ", estimate=" + estimate
				+ ", user=" + user + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
