package network.models;

/**
 * A response body for login request.
 * Contains credentials of the user being logged in.
 */
public class LoginRequestBody {
    private String email;
    private String password;

    public LoginRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
