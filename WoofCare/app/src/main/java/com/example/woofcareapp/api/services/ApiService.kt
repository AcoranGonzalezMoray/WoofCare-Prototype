package com.example.woofcareapp.api.services
import com.example.woofcareapp.api.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("api/v1/signin")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<User>

    @POST("api/v1/signup")
    suspend fun signUp(
        @Body registrationBody: User
    ): Response<voidResponse>

    @PUT("api/v1/user/{id}")
    suspend fun updateUser(
        @Path("id") id: Int
    ): Response<voidResponse>

    @DELETE("api/v1/user/{id}")
    suspend fun deleteAccount(
        @Path("id") id: Int
    ): Response<Unit>

    @Multipart
    @POST("api/v1/user/updateImage")
    suspend fun updateImageUser(
        @Part("userId") userId: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Void>
}


data class LoginResponse(
    val user: User,
    val token: String
)

data class voidResponse(
    val success: Boolean,
    val message: String? // Mensaje descriptivo opcional
)
