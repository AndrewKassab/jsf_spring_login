package andrewkassab.jsf_login.components;

public enum ToolbarAction {
    PROFILE("Profile", "#{toolbarBean.goToProfile}"),
    SETTINGS("Settings", "#{toolbarBean.goToSettings}"),
    LOGOUT("Logout", "#{loginBean.logout}");

    private final String label;
    private final String action;

    ToolbarAction(String label, String action) {
        this.label = label;
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public String getAction() {
        return action;
    }

}
