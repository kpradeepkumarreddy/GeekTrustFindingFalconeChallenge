package com.geektrust.findingfalcone.models

import com.google.gson.annotations.SerializedName

data class VehiclesTO(
        var name: String,
        @SerializedName("total_no")
        var vehiclesCount: Int,
        @SerializedName("max_distance")
        var maxDistance: Int,
        var speed: Int,
        var isSelected: Boolean
) {
    override fun equals(other: Any?): Boolean {
        val vehiclesTO = other as VehiclesTO
        return this.name == vehiclesTO.name
    }
}