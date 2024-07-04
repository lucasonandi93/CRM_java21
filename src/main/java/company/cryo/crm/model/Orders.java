package company.cryo.crm.model;

import java.sql.Timestamp;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Orders {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "order_label")
	private String orderLabel;
	
	@Column(name = "order_type")
	private String orderType;
	
	@Column(name = "order_status")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Column(name = "order_comment")
	private String orderComment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Users users;
	
	@OneToOne
	@JoinColumn(name="estimate_id", referencedColumnName = "estimate_id")
	private Estimates estimates;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private Customers customers;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Override
	public String toString() {
		return "Orders [id=" + id + ", orderLabel=" + orderLabel + ", orderType=" + orderType + ", orderStatus="
				+ orderStatus + ", orderComment=" + orderComment + ", users=" + users + ", estimates="
				+ ", customers=" + customers + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
