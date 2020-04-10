package com.geektrust.findingfalcone.models

import com.google.gson.annotations.SerializedName

data class FindFalconeRequestTO(
        var token: String?,
        @SerializedName("planet_names")
        var planetNames: List<String>?,
        @SerializedName("vehicle_names")
        var vehicleNames: List<String>?
)