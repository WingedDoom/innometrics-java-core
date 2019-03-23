package core;

import models.Activity;
import network.InnometricsApi;
import network.models.ActivityRequestBody;
import network.models.ActivityRequestBodyItem;
import network.models.LoginRequestBody;
import network.models.LoginResponseBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import storage.AuthTokenStorage;
import storage.PersistentActivitiesStorage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides the client with methods necessary to store {@link models.Activity} instances and
 * submit them to an Innometrics server.
 */
public class InnometricsCoreRepository {
    private static final int SUBMISSION_BATCH_SIZE = 100;

    private AuthTokenStorage tokenStorage;
    private PersistentActivitiesStorage activitiesStorage;
    private InnometricsApi innometricsApiService;
    private String currentBaseURL;

    /**
     * Creates a repository with given token and activities storage. Sets base URL to the default value
     * taken from {@link Constants}.
     * @param tokenStorage token storage that will provide the repository with an access token to the API.
     * @param activitiesStorage activities storage that will let the repository to store activities.
     */
    public InnometricsCoreRepository(AuthTokenStorage tokenStorage, PersistentActivitiesStorage activitiesStorage) {
        this.tokenStorage = tokenStorage;
        this.activitiesStorage = activitiesStorage;
        setBaseURL(Constants.DEFAULT_BASE_URL);
    }

    /**
     * Sets a custom server base URL to make requests to.
     * This action usually would invalidate your API access token, so make sure to
     * call {@code logout} in advance.
     * @param baseURL new custom base URL of an Innometrics API providing server.
     */
    public void setBaseURL(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        innometricsApiService = retrofit.create(InnometricsApi.class);
        currentBaseURL = baseURL;

    }

    // Core API

    public String getCurrentBaseURL() {
        return currentBaseURL;
    }

    /**
     * Attempts to authorize a user with given credentials. The method is executed synchronously.
     * @param login user's login.
     * @param password user's password.
     * @throws Exception thrown with an explanatory human-readable message in case the authorization failed.
     */
    public void login(String login, String password) throws Exception {
        LoginRequestBody credentials = new LoginRequestBody(login, password);
        Response<LoginResponseBody> loginResponse = innometricsApiService.login(credentials).execute();

        switch (loginResponse.code()) {
            case 200:
                // success response, attempt to serialize the body and store the received token
                LoginResponseBody body = loginResponse.body();
                if (body != null) {
                    tokenStorage.setToken(body.getToken());
                } else {
                    throw new Exception("Unknown error occurred");
                }
                break;
            case 400:
                throw new Exception("Incorrect data format. Probably the server API changed?");
            case 401:
                throw new Exception("Incorrect credentials.");
            case 404:
                throw new Exception("User with given email could not be found");
                default:
                    throw new Exception("Unexpected response code.");
        }
    }

    /**
     * If current {@code tokenStorage} contains an API token, attempts to end current user's session.
     * Does nothing otherwise.
     */
    public void logout() {
        String storedToken = tokenStorage.getToken();
        if (storedToken == null) return;

        try {
            innometricsApiService.logout(storedToken).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // delete the token
        tokenStorage.setToken(null);
    }

    /**
     * Adds tracked {@link Activity} instance into the repository. This is a synchronous request.
     * Repository then decides whether to store it in the {@code activitiesStorage} or submit to the server right away.
     * @param newActivity activity to submit.
     */
    public void addActivity(Activity newActivity) {
        String storedToken = tokenStorage.getToken();
        List<Activity> activities = Collections.singletonList(newActivity);

        if (storedToken == null)
            activitiesStorage.saveActivities(activities); // postpone submission

        // attempt to submit the activity right away
        try {
            // attempt to submit
            submitPojoActivities(storedToken, activities);
        } catch (Exception e) {
            // postpone submission
            activitiesStorage.saveActivities(activities);
        }
    }

    /**
     * Tells the repository that it is appropriate time to submit all persisted activities to the server.
     * This is a synchronous request.
     * During execution, the repository queries its {@code activitiesStorage} for batches of activities, until
     * all the activities are submitted.
     * @throws Exception an exception containing a human readable message, describing what went wrong during submission.
     */
    public void submitActivities() throws Exception {
        String storedToken = tokenStorage.getToken();
        if (storedToken == null)
            throw new Exception("User authentication is first required before submitting activities.");

        List<Activity> activitiesToSubmit = activitiesStorage.getActivitiesBatch(SUBMISSION_BATCH_SIZE);
        if (activitiesToSubmit.isEmpty()) return; // submitted all activities

        // attempt to submit
        submitPojoActivities(storedToken, activitiesToSubmit);

        // submission successful, delete activities batch
        activitiesStorage.removeActivities(activitiesToSubmit);

        // proceed to next batch
        submitActivities();
    }

    private void submitPojoActivities(String token, List<Activity> activitiesToSubmit) throws Exception {
        ActivityRequestBody submissionBody = new ActivityRequestBody(
                activitiesToSubmit.stream().map(ActivityRequestBodyItem::new).collect(Collectors.toList())
        );

        int responseCode = innometricsApiService.submitActivity(token, submissionBody).execute().code();
        if (responseCode != 201) {
            throw new Exception("Could not create activities.");
        }
    }
}
