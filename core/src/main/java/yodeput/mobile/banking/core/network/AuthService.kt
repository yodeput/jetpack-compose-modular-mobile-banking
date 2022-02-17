package yodeput.mobile.banking.core.network

import yodeput.mobile.banking.core.request.LoginRequest
import yodeput.mobile.banking.core.response.BalanceResponse
import yodeput.mobile.banking.core.response.LoginResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<LoginResponse>

    @POST("/register")
    suspend fun register(@Body body: LoginRequest): ApiResponse<LoginResponse>

    @GET("/balance")
    suspend fun balance(): ApiResponse<BalanceResponse>
}