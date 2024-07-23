package company.cryo.crm.model;

public enum OrderStatus {
	NON_LIVREE("Non livrée"),
	EN_COURS_DE_LIVRAISON("En cours de livraison"),
	LIVREE("Livrée"),
	ARCHIVEE("Archivée");

	private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
