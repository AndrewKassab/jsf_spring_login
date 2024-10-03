package andrewkassab.jsf_login.beans;

import andrewkassab.jsf_login_common.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;

    private String confirmPassword;

    private HttpClient httpClient;

    private String BASE_URL = "http://localhost:8081";

    private String REGISTER = "/register";

    private String LOGIN = "/login";

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);  // Keeps messages for the next request

        if (authenticate(username, password)) {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("username", username);
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml");
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Credentials.", null));
        }
    }

    private boolean authenticate(String username, String password) {
        try {
            LoginRequest loginRequest = new LoginRequest(username, password);

            String jsonInputString = objectMapper.writeValueAsString(loginRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL.concat(LOGIN)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void register() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);  // Keeps messages for the next request

        if (!password.equals(confirmPassword)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords must match", null));
            return;
        }

        try {
            LoginRequest loginRequest = new LoginRequest(username, password);

            String jsonInputString = objectMapper.writeValueAsString(loginRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL.concat(REGISTER)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HTTP_CREATED) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account successfully created", null));
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            }
            else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already taken", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logged out successfully", null));
        facesContext.getExternalContext().redirect("login.xhtml");
    }

    public void checkLogin() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            context.getExternalContext().redirect("login.xhtml");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
