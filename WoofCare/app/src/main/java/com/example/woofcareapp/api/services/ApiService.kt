package com.example.woofcareapp.api.services
import com.example.woofcareapp.api.models.Advertisement
import com.example.woofcareapp.api.models.Dog
import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.models.Product
import com.example.woofcareapp.api.models.Request
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("api/v1/signin")
    suspend fun signIn(
        @Body requestBody: RequestBody
    ): Response<User>

    @POST("api/v1/signup")
    suspend fun signUp(
        @Body registrationBody: User
    ): Response<ResponseMessage>

    @GET("api/v1/user/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<User>


    @PUT("api/v1/user/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: User
    ): Response<JSONObject>

    @DELETE("api/v1/user/{id}")
    suspend fun deleteAccount(
        @Path("id") id: Int
    ): Response<User>

    @Multipart
    @POST("api/v1/image")
    suspend fun updateImageUser(
        @Part("objectId") objectId: RequestBody,
        @Part("objectType") objectType: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<String>


    @GET("api/v1/product")
    suspend fun getProducts(): Response<List<Product>>

    @POST("api/v1/product")
    suspend fun createProduct(
        @Body product: Product
    ): Response<ResponseMessage>

    @GET("api/v1/product/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Response<Product>

    @PUT("api/v1/product/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: Product
    ): Response<ResponseMessage>

    @DELETE("api/v1/product/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): Response<ResponseMessage>


    @GET("api/v1/request")
    suspend fun getRequests(): Response<List<Request>>

    @POST("api/v1/request")
    suspend fun createRequest(
        @Body request: Request
    ): Response<ResponseMessage>

    @GET("api/v1/request/{id}")
    suspend fun getRequest(
        @Path("id") id: Int
    ): Response<Request>

    @PUT("api/v1/request/{id}")
    suspend fun updateRequest(
        @Path("id") id: Int,
        @Body request: Request
    ): Response<ResponseMessage>

    @DELETE("api/v1/request/{id}")
    suspend fun deleteRequest(
        @Path("id") id: Int
    ): Response<Unit>


    @GET("api/v1/service")
    suspend fun getServices(): Response<List<Service>>

    @POST("api/v1/service")
    suspend fun createService(
        @Body service: Service
    ): Response<ResponseMessage>

    @GET("api/v1/service/{id}")
    suspend fun getService(
        @Path("id") id: Int
    ): Response<Service>

    @PUT("api/v1/service/{id}")
    suspend fun updateService(
        @Path("id") id: Int,
        @Body service: Service
    ): Response<ResponseMessage>

    @DELETE("api/v1/service/{id}")
    suspend fun deleteService(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("api/v1/dog")
    suspend fun getDogs(): Response<List<Dog>>

    @POST("api/v1/dog")
    suspend fun createDog(
        @Body dog: Dog
    ): Response<ResponseMessage>

    @GET("api/v1/dog/{id}")
    suspend fun getDog(
        @Path("id") id: Int
    ): Response<Dog>

    @PUT("api/v1/dog/{id}")
    suspend fun updateDog(
        @Path("id") id: Int,
        @Body dog: Dog
    ): Response<ResponseMessage>

    @DELETE("api/v1/dog/{id}")
    suspend fun deleteDog(
        @Path("id") id: Int
    ): Response<Unit>


    @GET("api/v1/advertisement")
    suspend fun getAdvertisements(): Response<List<Advertisement>>

    @POST("api/v1/advertisement")
    suspend fun createAdvertisement(
        @Body advertisement: Advertisement
    ): Response<ResponseMessage>

    @GET("api/v1/advertisement/{id}")
    suspend fun getAdvertisement(
        @Path("id") id: Int
    ): Response<Advertisement>

    @PUT("api/v1/advertisement/{id}")
    suspend fun updateAdvertisement(
        @Path("id") id: Int,
        @Body advertisement: Advertisement
    ): Response<ResponseMessage>

    @DELETE("api/v1/advertisement/{id}")
    suspend fun deleteAdvertisement(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("api/v1/message")
    suspend fun getMessages(): Response<List<Message>>

    @POST("api/v1/message")
    suspend fun createMessage(
        @Body message: Message
    ): Response<ResponseMessage>

    @DELETE("api/v1/message/{id}")
    suspend fun deleteMessage(
        @Path("id") id: Int
    ): Response<Unit>
}

data class ResponseMessage(
    val message: String
)
