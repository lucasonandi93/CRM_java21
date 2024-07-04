package company.cryo.crm.form;

import company.cryo.crm.model.CustomerStatus;
import company.cryo.crm.model.Users;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class CustomerForm {

	private Integer customerId;
	
	@NotNull
	@Size(min=1, max = 50)
	private String firstname;

	@NotNull
	@Size(min=1, max = 50)
    private String lastname;

	@NotNull
	@Size(min=1, max = 255)
    private String email;

	@NotNull
	@Size(min=1, max = 50)
    private String company;

	@Size(min=1, max = 20)
    private String officePhone;

	@Size(min=1, max = 20)
    private String mobilePhone;
	@NotNull
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;
	
	private Boolean guarantee;

    private String customerComment;

    private Users users; 
    
    private Integer userId;
    
}

