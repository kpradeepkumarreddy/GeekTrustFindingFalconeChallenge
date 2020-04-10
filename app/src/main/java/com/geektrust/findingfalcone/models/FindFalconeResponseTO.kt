package com.geektrust.findingfalcone.models

import com.google.gson.annotations.SerializedName

data class FindFalconeResponseTO(
        @SerializedName("planet_name")
        var planetName: String,
        var status: String,
        var error: String
)