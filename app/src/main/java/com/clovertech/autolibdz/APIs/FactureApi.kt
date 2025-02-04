package com.clovertech.autolibdz.APIs

import com.clovertech.autolibdz.DataClasses.Bill
import com.clovertech.autolibdz.DataClasses.DownResponse
import com.clovertech.autolibdz.DataClasses.Facture
import com.clovertech.autolibdz.DataClasses.Vehicle
import com.clovertech.autolibdz.ui.card.Card
import com.clovertech.autolibdz.utils.Constants.Companion.Bill_BASE_URL
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FactureApi {
    @GET("all")
    suspend fun getFactures():Response<MutableList<Facture>>

    companion object{
        operator fun invoke():FactureApi{
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl+"bill/")
                    .build()
                    .create(FactureApi::class.java)

        }
    }
    @GET("download/{idBill}")
    suspend fun getBillByID(@Path("idBill") idBill : Int): Response<DownResponse>
}