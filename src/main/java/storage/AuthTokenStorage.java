package storage;

/**
 * Clients may implement this interface to provide InnometricsJavaCore's with a
 * secure platform-specific storage for persisting user's API auth token.
 */
public interface AuthTokenStorage {
    /**
     * Implement this method to receive an API auth token from InnometricsJavaCore and store it
     * in a platform-specific and secure way.
     * @param token API session token. If token is expired, {@code null} will be passed.
     */
    void setToken(String token);

    /**
     * Retrieves an API auth token.
     * @return API auth token.
     */
    String getToken();
}
