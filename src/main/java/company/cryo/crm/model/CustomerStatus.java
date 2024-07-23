package company.cryo.crm.model;

public enum CustomerStatus {
    LEAD("LEAD"),
    INTERESSE("INTERESSE"),
    CLIENT("CLIENT");

    private final String displayName;

    CustomerStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
