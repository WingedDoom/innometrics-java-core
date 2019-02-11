package network;

import network.models.LoginRequestBody;
import network.models.LoginResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * A Retrofit2 interface for Innometrics API wrapper.
 */
public interface InnometricsApi {
    @POST("/login")
    Call<LoginResponseBody> login(@Body LoginRequestBody userInfo);

    @POST("/logout")
    Call<Void> logout(@Header("Authorization") String token);
}
