package company.cryo.crm.dto;


import company.cryo.crm.model.CustomerStatus;
import company.cryo.crm.model.Estimates;
import company.cryo.crm.model.Orders;
import company.cryo.crm.model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomersDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String company;


    private String officePhone;


    private String mobilePhone;


    private CustomerStatus customerStatus;

    private Boolean guarantee;

    private String customerComment;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private List<Orders> orders;

    private List<Estimates> estimates;

    private Users users;

    }

