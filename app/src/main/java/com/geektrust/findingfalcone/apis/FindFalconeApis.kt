package com.geektrust.findingfalcone.apis

import com.geektrust.findingfalcone.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FindFalconeApis {

    @GET("planets")
    fun getPlanets(): Call<List<PlanetsTO>>

    @GET("vehicles")
    fun getVehicles(): Call<List<VehiclesTO>>

    @POST("find")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun findFalcone(@Body findFalconeRequestTO: FindFalconeRequestTO): Call<FindFalconeResponseTO>

    @POST("token")
    @Headers("Accept:application/json")
    fun getToken(): Call<TokenResponseTO>

}