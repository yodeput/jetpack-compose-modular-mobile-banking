package yodeput.mobile.banking.core.network

import yodeput.mobile.banking.core.request.TransferRequest
import yodeput.mobile.banking.core.response.TransactionsResponse
import yodeput.mobile.banking.core.response.TransferResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TrxService {

    @POST("/transfer")
    suspend fun transfer(@Body body: TransferRequest): ApiResponse<TransferResponse>

    @GET("/transactions")
    suspend fun transactions(): ApiResponse<TransactionsResponse>
}