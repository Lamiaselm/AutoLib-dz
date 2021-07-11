package api

import com.clovertech.autolibdz.DataClasses.Locataire
import model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/3")
    suspend fun getUser():User

    @GET("users/{id}")
    suspend fun getUserById(@Path("id")id :Int):User
    @GET("locataires/{idUser}")
    fun getPointByUser(@Path("idUser")idUser :Int): Call<Locataire>
}