package company.cryo.crm.form;


import java.sql.Timestamp;

import company.cryo.crm.model.EstimateStatus;
import company.cryo.crm.model.UserGrant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import company.cryo.crm.model.UserGrant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public class UserForm {
 	private Integer id;
 	
 	@NotNull
    private String firstname;
 	
 	@NotNull
    private String lastname;
    
    @Email
    @NotNull
    private String email;
    
 
    private String userPassword;
    
    @NotNull
    private  UserGrant grantName;
    
    private Boolean active;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
		
		
	public String getUserPassword() {
			return userPassword;
		}

	public UserGrant getGrantName() {
		return grantName;
	}

	public void setGrantName(UserGrant grantName) {
		this.grantName = grantName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserForm [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", userPassword=" + userPassword + ", grantName=" + grantName + ", active=" + active + "]";
	}


   
    
}
