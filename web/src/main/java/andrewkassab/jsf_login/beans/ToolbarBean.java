package andrewkassab.jsf_login.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named("toolbarBean")
@SessionScoped
public class ToolbarBean implements Serializable {

    public void goToProfile() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
    }

    public void goToSettings() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("settings.xhtml");
    }

}
