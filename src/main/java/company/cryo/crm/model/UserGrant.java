package company.cryo.crm.model;

public enum UserGrant {
	RESPONSABLE("Responsable"),
	GESTIONNAIRE_DES_VENTES("Gestionnaire des ventes"),
	SERVICE_CLIENT("Service client");
	

	private final String displayName;

	UserGrant(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
