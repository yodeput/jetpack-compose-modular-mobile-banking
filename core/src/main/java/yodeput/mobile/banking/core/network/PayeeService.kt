package yodeput.mobile.banking.core.network

import yodeput.mobile.banking.core.response.PayeesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface PayeeService {

    @GET("/payees")
    suspend fun payees(): ApiResponse<PayeesResponse>
}