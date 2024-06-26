package com.ashrafovtaghi.onboarding_domain.usecase

import com.ashrafovtaghi.core.R
import com.ashrafovtaghi.core.util.UIText

class ValidateNutrients {
    operator fun invoke(
        carbRatioText: String,
        proteinRatioText: String,
        fatRatioText: String
    ): Result {
        val carbRatio = carbRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()
        if (carbRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(UIText.StringResource(R.string.error_invalid_values))
        }
        if (carbRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(UIText.StringResource(R.string.error_not_100_percent))
        }
        return Result.Success(carbRatio / 100f, proteinRatio / 100f, fatRatio / 100f)
    }

    sealed class Result {
        data class Success(val carbsRatio: Float, val proteinRatio: Float, val fatRatio: Float) :
            Result()

        data class Error(val message: UIText) : Result()
    }
}