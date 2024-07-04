package company.cryo.crm.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserAction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@Column(name = "method")
	private String method;
	
	@Column(name = "action_realisee")
	private String action;
	
	@Column(name = "created_at", updatable = false)
	private Timestamp timestamp;

	// Constructor con todos los campos
	public UserAction(Users user, String method, String action, Timestamp timestamp) {
		this.user = user;
		this.method = method;
		this.action = action;
		this.timestamp = timestamp;
	}
}
