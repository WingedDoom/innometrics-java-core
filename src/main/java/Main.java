import network.InnometricsApi;
import network.models.LoginRequestBody;
import network.models.LoginResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://innometric.guru:8120")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InnometricsApi innometricsApi = retrofit.create(InnometricsApi.class);

        try {

            Response<Void> response = innometricsApi.logout("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NTgwMzg4MzUsImlhdCI6MTU1NTQ0NjgzNSwic3ViIjoiNWM5ZDM3MTQ1ZjYyN2Q2OTVmODAwNGE2In0.P7V2ID5WUKE3TK8uDTSwzyM0h2DfPGpAugr4CxgzqcY").execute();
            System.out.println(response.code());
            if (response.isSuccessful()) {
                System.out.println(response.body().toString());
            } else {
                System.out.println(response.errorBody().string());
            }
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
