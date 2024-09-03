package com.org.marton.studio.project.eldarwallet.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface QRCodeApi {
    @POST("classic")
   suspend fun generateQrCode(@Query("text") data: String): Call<ResponseBody>
}
