package network;

/**
 * Clients may implement this interface to handle InnometricsJavaCore's successful authorization
 * and store a given session token in a platform-specific secure manner.
 */
public interface AuthTokenStore {
    /**
     * Implement this method to receive an API auth token from InnometricsJavaCore and store it
     * in a platform-specific and secure way.
     * @param token API session token. If token is expired, {@code null} will be passed.
     */
    void setToken(String token);
}
