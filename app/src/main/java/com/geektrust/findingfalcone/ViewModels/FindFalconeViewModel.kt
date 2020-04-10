package com.geektrust.findingfalcone.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.geektrust.findingfalcone.apis.FindFalconeApis
import com.geektrust.findingfalcone.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindFalconeViewModel() : ViewModel() {
    var planetList: List<PlanetsTO>? = null
    var vehicleList: List<VehiclesTO>? = null
    var tokenResponseTO: TokenResponseTO? = null
    var findFalconeResponseTO: FindFalconeResponseTO? = null
    var planetNames = mutableListOf<String>()
    var vehicleNames = mutableListOf<String>()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://findfalcone.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val findFalconeApis = retrofit.create(FindFalconeApis::class.java)

    fun getPlanets() {
        Log.d("log", "getPlanets() in FindFalconeViewModel")
        findFalconeApis.getPlanets().enqueue(object : Callback<List<PlanetsTO>> {
            override fun onFailure(call: Call<List<PlanetsTO>>, t: Throwable) {
                Log.d("log", "getPlanets()::onFailure() ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<List<PlanetsTO>>, response: Response<List<PlanetsTO>>) {
                // return from here if the response.body returns null
                response.body() ?: return
                planetList = response.body()!!
                for (planetsTO in planetList!!) {
                    planetsTO.isSelected = false
                    Log.d("log", "getPlanets()::onResponse() PlanetsTO = $planetsTO")
                }
            }
        })
    }

    fun getVehicles() {
        Log.d("log", "getVehicles() in FindFalconeViewModel")

        findFalconeApis.getVehicles().enqueue(object : Callback<List<VehiclesTO>> {
            override fun onFailure(call: Call<List<VehiclesTO>>, t: Throwable) {
                Log.d("log", "getVehicles()::onFailure() ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<List<VehiclesTO>>, response: Response<List<VehiclesTO>>) {
                // return from here if the response.body returns null
                response.body() ?: return
                vehicleList = response.body()!!
                for (vehicleTO in vehicleList!!) {
                    vehicleTO.isSelected = false
                    Log.d("log", "getVehicles()::onResponse() vehicleTO = $vehicleTO")
                }
            }
        })
    }

    fun findFalcone() {
        // first get the token and then make the findFalcone call
        Log.d("log", "getToken() in FindFalconeViewModel")
        findFalconeApis.getToken().enqueue(object : Callback<TokenResponseTO> {
            override fun onFailure(call: Call<TokenResponseTO>, t: Throwable) {
                Log.d("log", "getToken()::onFailure() ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<TokenResponseTO>,
                                    response: Response<TokenResponseTO>) {
                Log.d("log", "getToken()::onResponse() tokenResponseTO = ${response.body()}, " +
                        "isSuccessful = ${response.isSuccessful},  headers = ${response.headers()}, " +
                        "message = ${response.message()}, code = ${response.code()}, " +
                        "errorBody = ${response.errorBody()}")
                // return from here if the response.body returns null
                response.body() ?: return
                tokenResponseTO = response.body()

                val findFalconeRequestTO = FindFalconeRequestTO(null, null, null)
                findFalconeRequestTO.token = tokenResponseTO?.token
                findFalconeRequestTO.planetNames = planetNames
                findFalconeRequestTO.vehicleNames = vehicleNames

                Log.d("log", "findFalcone() in FindFalconeViewModel")
                findFalconeApis.findFalcone(findFalconeRequestTO).enqueue(object : Callback<FindFalconeResponseTO> {
                    override fun onFailure(call: Call<FindFalconeResponseTO>, t: Throwable) {
                        Log.d("log", "findFalcone()::onFailure() ${t.localizedMessage}")
                    }

                    override fun onResponse(call: Call<FindFalconeResponseTO>, response: Response<FindFalconeResponseTO>) {
                        Log.d("log", "findFalcone()::onResponse() findFalconeResponseTO = ${response.body()}, " +
                                "isSuccessful = ${response.isSuccessful},  headers = ${response.headers()}, " +
                                "message = ${response.message()}, code = ${response.code()}, " +
                                "errorBody = ${response.errorBody()}")
                        // return from here if the response.body returns null
                        response.body() ?: return
                        findFalconeResponseTO = response.body()!!
                    }
                })
            }
        })

    }
}