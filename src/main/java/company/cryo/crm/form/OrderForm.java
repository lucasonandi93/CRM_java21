package company.cryo.crm.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import company.cryo.crm.model.OrderStatus;

public class OrderForm {

	private Integer id;

	@NotNull
	@Size(min=1, max=50)
	private String orderLabel;

	@NotNull
	private OrderStatus orderStatus;

	@NotNull
	@Size(min=1, max=50)
	private String orderType;

	private String orderComment;

	@NotNull
	private Integer estimatesId;

	
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderComment() {
		return orderComment;
	}

	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
	}

	public Integer getEstimatesId() {
		return estimatesId;
	}

	public void setEstimatesId(Integer estimatesId) {
		this.estimatesId = estimatesId;
	}

}
