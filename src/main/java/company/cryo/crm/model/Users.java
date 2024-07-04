package company.cryo.crm.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Setter
@NoArgsConstructor
public class Users {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "email")
	private String email;

	//Pas de getter pour le password pour la sécurité
	@Column(name = "user_password")
	private String userPassword;
	
	@Column(name = "grant_name")
	@Enumerated(EnumType.STRING)
	private UserGrant grantName;
	
	@Column(name = "active")
	private Boolean active;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;
	
	@CreationTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "users")
	private List<Customers> customers;
	
	@OneToMany(mappedBy = "users")
	private List<Orders> orders;
	
	@OneToMany(mappedBy = "users")
	private List<Estimates> estimates;
	

	public Integer getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}
	
	public String getUserPassword() {
		return userPassword;
	}


	public UserGrant getGrantName() {
		return grantName;
	}

	public Boolean getActive() {
		return active;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public List<Customers> getCustomers() {
		return customers;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public List<Estimates> getEstimates() {
		return estimates;
	}

	
}


