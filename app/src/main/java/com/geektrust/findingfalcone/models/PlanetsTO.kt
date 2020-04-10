package com.geektrust.findingfalcone.models

data class PlanetsTO(
        var name: String?,
        var distance: Int?,
        var isSelected: Boolean?
) {
    override fun equals(other: Any?): Boolean {
        val planetsTO = other as PlanetsTO
        return this.name == planetsTO.name
    }
}