package network;

/**
 * Clients may implement this interface to allow InnometricsJavaCore to retrieve an API authorization
 * token stored elsewhere.
 */
public interface AuthTokenProvider {
    /**
     * Retrieves an API auth token.
     * @return API auth token.
     */
    String getToken();
}