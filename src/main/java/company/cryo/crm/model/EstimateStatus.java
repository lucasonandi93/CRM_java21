package company.cryo.crm.model;

public enum EstimateStatus {

    VALIDE("Validé"),
    EN_COURS("En Cours"),
    REFUSE("Refusé"),
    ARCHIVE("Archivé");

	private final String displayName;

    EstimateStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
