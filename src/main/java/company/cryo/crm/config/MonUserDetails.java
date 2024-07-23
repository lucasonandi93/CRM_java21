package company.cryo.crm.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import company.cryo.crm.model.UserGrant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import company.cryo.crm.model.Users;

public class MonUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 681232469941528115L;
	private final Users user;
	
	public MonUserDetails(Users user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> droits = new ArrayList<>();
	
		switch (this.user.getGrantName()) {
			case UserGrant.RESPONSABLE:
				droits.add(new SimpleGrantedAuthority("RESPONSABLE"));
				droits.add(new SimpleGrantedAuthority("GESTIONNAIRE_DES_VENTES"));
				droits.add(new SimpleGrantedAuthority("SERVICE_CLIENT"));
				System.out.println(droits);
				break;
			case UserGrant.GESTIONNAIRE_DES_VENTES:
				droits.add(new SimpleGrantedAuthority("GESTIONNAIRE_DES_VENTES"));
				droits.add(new SimpleGrantedAuthority("SERVICE_CLIENT"));
				break;
				
			case UserGrant.SERVICE_CLIENT:
				droits.add(new SimpleGrantedAuthority("SERVICE_CLIENT"));
				break;
		}
		
				
		System.out.println(droits);
		return droits;
	}

	@Override
	public String getPassword() {
		return this.user.getUserPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getFirstname()+' '+this.user.getLastname();
	}

	/**
	 * Nouveau par rapport à spring sécu
	 * @return
	 */
	public String getMail() {
		return this.user.getEmail();
	}
	
	public Integer getId() {
		return this.user.getId();
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, String> getInfos() {
		Map<String, String> infos = new HashMap<String, String>();
		infos.put("firstname", this.user.getFirstname());
		infos.put("lastname", this.user.getLastname());
		infos.put("email", this.user.getEmail());
		infos.put("grant", this.user.getGrantName().getDisplayName());
		infos.put("createdAt", this.user.getCreatedAt().toLocaleString());
		return infos;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.user.getActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.user.getActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.user.getActive();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.getActive();
	}

}

