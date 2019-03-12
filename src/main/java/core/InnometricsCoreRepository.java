package core;

import models.Activity;
import network.InnometricsApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import storage.AuthTokenStorage;
import storage.PersistentActivitiesStorage;

/**
 * Provides the client with methods necessary to store {@link models.Activity} instances and
 * submit them to an Innometrics server.
 */
public class InnometricsCoreRepository {
    private static final String DEFAULT_BASE_URL = "";

    private AuthTokenStorage tokenStorage;
    private PersistentActivitiesStorage activitiesStorage;
    private InnometricsApi innometricsApiService;

    public InnometricsCoreRepository(AuthTokenStorage tokenStorage, PersistentActivitiesStorage activitiesStorage) {
        this.tokenStorage = tokenStorage;
        this.activitiesStorage = activitiesStorage;
        setBaseURL(DEFAULT_BASE_URL);
    }

    /**
     * Sets a custom server base URL to make requests to.
     * @param baseURL new custom base URL of an Innometrics API providing server.
     */
    public void setBaseURL(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        innometricsApiService = retrofit.create(InnometricsApi.class);

    }

    // Core API

    /**
     * Attempts to authorize a user with given credentials.
     * @param login user's login.
     * @param password user's password.
     * @throws Exception thrown with an explanatory human-readable message in case the authorization failed.
     */
    void login(String login, String password) throws Exception {

    }

    /**
     * If current {@code tokenStorage} contains an API token, attempts to end current user's session.
     * Does nothing otherwise.
     */
    void logout() {

    }

    /**
     * Adds tracked {@link Activity} instance into the repository.
     * Repository then decides whether to store it in the {@code activitiesStorage} or submit to the server right away.
     * @param newActivity
     */
    void addActivity(Activity newActivity) {

    }

    /**
     * Tells the repository that it is appropriate time to submit all persisted activities to the server.
     * This is a synchronous request.
     */
    void submitActivities() {

    }
}
