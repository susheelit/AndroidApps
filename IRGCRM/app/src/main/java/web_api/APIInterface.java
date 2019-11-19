package web_api;

import com.irgsol.irg_crm.models.ModelUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    // http://localhost/irg_crm/api/userLogin.php?email_id=test@test.com&password=12345

  /*  @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();
*/
   /* @POST("/api/users")
    Call<User> createUser(@Body User user);
*/
   /* @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);
*/
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    @GET("/api/userLogin.php?")
    Call<ModelUser> userLogin(@Query("email_id") String email_id, @Query("password") String password);

}
