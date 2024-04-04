package com.ashrafovtaghi.tracker_data.mapper

import com.ashrafovtaghi.tracker_data.remote.dto.Product
import com.ashrafovtaghi.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    val carbsPer100g = this.nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = this.nutriments.proteins100g.roundToInt()
    val fatPer100g = this.nutriments.fat100g.roundToInt()
    val caloriesPer100g = this.nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        name = this.productName ?: return null,
        imageUrl = this.imageFrontThumbUrl,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g,
        caloriePer100g = caloriesPer100g
    )
}