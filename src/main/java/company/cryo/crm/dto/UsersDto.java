package company.cryo.crm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import company.cryo.crm.model.Customers;
import company.cryo.crm.model.Estimates;
import company.cryo.crm.model.Orders;
import company.cryo.crm.model.UserGrant;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersDto implements Serializable {
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private Integer id;
	 	
	    private String firstname;
	    
	    private String lastname;
	    
	    @Email
	    private String email;
	    
	    private String userPassword;
	    
	    private UserGrant grantName;
	    
	    private Boolean active;
	    
	    private Timestamp createdAt;
	    
	    private Timestamp updatedAt;
	    
	    private List<Customers> customers;
	    
	    private List<Orders> orders;
	    
	    private List<Estimates> estimates;
	    
	
}
