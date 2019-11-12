package web_api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    private static Retrofit retrofit = null;

    //http://localhost/irg_crm/api/userLogin.php?email_id=test@test.com&password=12345

    private static final String baseUrl="http://localhost/irg_crm/";
    static Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);

        return retrofit;
    }
}
