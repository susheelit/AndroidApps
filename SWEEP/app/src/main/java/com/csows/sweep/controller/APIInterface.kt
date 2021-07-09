package com.csows.sweep.controller

import com.csows.sweep.models.ELCList
import com.csows.sweep.models.EventList
import com.csows.sweep.models.ResourceList
import com.csows.sweep.models.UserList
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

     /*GET API*/
    //login for all
    //http://164.100.72.248/CeoDelhiOffice/api/Login/GetLogin?OfficerMobile=8285121470
    @GET("Login/GetLogin")
    fun getLoginDetails(@Query("OfficerMobile") mobile: String?): Call<UserList?>?

    // list of ELC category
    // ElcCategory IS BLO , VAF, Nodal officer school college
   // http://164.100.72.248/CeoDelhiOffice/api/BindDropDown/ListElcCategory
    @GET("BindDropDown/ListElcCategory")
    fun getELCList(): Call<ELCList?>?

    @GET("BindDropDown/ListResourceMaterial")
    fun getListResourceList(): Call<ResourceList?>?

    @GET("EventName/GetEvent")
    fun getEventList():Call<EventList>?


    /*POST API*/

    @FormUrlEncoded
    @POST("EventName/EventEntry")
    fun insertEvent(@Field("EventName")eventName:String, @Field("EventDate")eventDate:String,
                    @Field("Remark")remark:String, @Field("EntryBy_Mobile") userId:String) : Call<String>

/*
// list of Resource Material
http://164.100.72.248/CeoDelhiOffice/api/BindDropDown/ListResourceMaterial



Himanshu:15:55:33
http://164.100.72.248/CeoDelhiOffice/api/sveep/ResourceMaterialEntry

Himanshu:15:55:56
{
    "SubjectName":"testing",
    "Link":"test.jpg",
    "ResourceMaterialID":"1",
    "ElcCategoryID":"2",
    "AttachmentString":"JVBERi0xLjQKMSAwIG9iago8PAovVGl0bGUgKP7/AEEAbgBkAHIAbwBpAGQAIAB1AG4AawBuAG8A"


}

Himanshu:15:56:14
http://164.100.72.248/CeoDelhiOffice/api/sveep/ResourceMaterialEntryList?ResourceMaterialID=1&ElcCategoryID=2*/


    /*  @GET("AuthLogin/getLoginDetails")
      fun getMemberDetails(@Query("Mobile") Mobile: String?): Call<UserList?>?

      // api/AuthLogin/getLoginByUserId?userid=csowsop&UserPswd=csows@123
      @GET("AuthLogin/getLoginByUserId")
      fun getLoginDetails(@Query("userid") userName: String?, @Query("UserPswd") password: String?): Call<UserList?>?

      @GET("getPdf/getPdf")
      fun getDocumentsList(): Call<DocumentsList?>?

      @GET("/")
      fun getLoginDetails1(): Observable<List<User>>

      *//*@GET("ServiceRequest/ShowServiceRequest")
    fun getServiceList(): Call<ServiceList?>?*//*

    @GET("GetServiveReqByUserID/GetServiveRequest")
    fun getServiceList(@Query("UserId") userId: String?): Call<ServiceList?>?

    @GET("Account/GetAccountDetails")
    fun getPaymentHistory(@Query("UserId") userId: String?): Call<PaymentsList?>?


    *//* @Multipart
    @POST("ServiceRequest/EntryServiceRequest")
    Call<String> requestService(
            @Part MultipartBody.Part file,
            @Part("PdfName") RequestBody fileName,
            @Part MultipartBody.Part photo,
            @Part("PhotoName") RequestBody photoName,
            @Part("BranchName") String branchName,
            @Part("ServiceType") String serviceType,
            @Part("IssueDescryption") String issueDescription,
            @Part("EstimateCost") String estimateCost,
            @Part("InsertBy") String insertBy,
            @Part("InsertByMobile") String insertByMobile);
*//*
    @POST("MaintananceService/InsertServiceRequest")
    @FormUrlEncoded
    fun requestService(
        @Field("UserID") userID: String?,
        @Field("BranchName") branchName: String?,
        @Field("ServiceType") serviceType: String?,
        @Field("IssueDescryption") issueDescription: String?,
        @Field("EstimateCost") estimateCost: String?,
        @Field("InsertBy") insertBy: String?,
        @Field("InsertByMobile") insertByMobile: String?,
        @Field("PhotoString") photoStr: String?,
        @Field("PhotoName") photoName: String?,
        @Field("PdfString") pdfStr: String?,
        @Field("PdfName") fileName: String?
    ): Call<String?>?

    @POST("Account/InsertAccountDetails")
    @FormUrlEncoded
    fun insertPaymentDetails(
        @Field("UserID") userID: String?,
        @Field("ServiceName") serviceName: String?,
        @Field("PayableAmount") payableAmount: String?,
        @Field("PaymentMode") paymentMode: String?,
        @Field("ChequeNo") chequeNo: String?,
        @Field("BankName") bankName: String?,
        @Field("TransactionNo") transactionNo: String?,
        @Field("TransactionDate") transactionDate: String?,
        @Field("Photo") photo: String?,
        @Field("Remark") remark: String?,
        @Field("PhotoString") photoString: String?
    ): Call<String?>?
*/
}