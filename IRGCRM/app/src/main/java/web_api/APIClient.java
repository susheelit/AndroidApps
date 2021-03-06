package web_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    //http://localhost/irg_crm/api/userLogin.php?email_id=test@test.com&password=12345
    //http://192.168.0.100/irg_crm/api/userLogin.php?email_id=test@test.com&password=123456

  //  private static final String baseUrl="http://10.0.2.2/irg_crm/";
   private static final String baseUrl="http://192.168.0.105/irg_crm/";
    public static Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        return retrofit;
    }
}
