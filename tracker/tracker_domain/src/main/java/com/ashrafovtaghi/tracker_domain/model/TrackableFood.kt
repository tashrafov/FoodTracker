package com.ashrafovtaghi.tracker_domain.model

data class TrackableFood(
    val name:String,
    val imageUrl:String?,
    val caloriePer100g:Int,
    val carbsPer100g:Int,
    val proteinPer100g:Int,
    val fatPer100g:Int,
)