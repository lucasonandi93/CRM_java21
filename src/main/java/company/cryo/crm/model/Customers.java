package company.cryo.crm.model;

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
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Customers {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String company;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "customer_status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    private Boolean guarantee;

    @Column(name = "customer_comment")
    private String customerComment;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "customers")
	private List<Orders> orders;
    
    @OneToMany(mappedBy = "customers")
   	private List<Estimates> estimates;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users users;
    
}
