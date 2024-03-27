package com.ashrafovtaghi.core.domain.preferences

import com.ashrafovtaghi.core.domain.models.ActivityLevel
import com.ashrafovtaghi.core.domain.models.Gender
import com.ashrafovtaghi.core.domain.models.GoalType
import com.ashrafovtaghi.core.domain.models.UserInfo

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveHeight(height: Int)
    fun saveWeight(weight: Float)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveGoalType(goalType: GoalType)
    fun saveCarbRatio(carbRatio: Float)
    fun saveProteinRatio(proteinRatio: Float)
    fun saveFatRatio(fatRatio: Float)

    fun loadUserInfo(): UserInfo

    companion object{
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_HEIGHT = "height"
        const val KEY_WEIGHT = "weight"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_CARB_RATIO = "carb_ratio"
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val KEY_FAT_RATIO = "fat_ratio"
    }
}