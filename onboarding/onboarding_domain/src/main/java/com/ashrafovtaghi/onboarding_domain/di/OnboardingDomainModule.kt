package com.ashrafovtaghi.onboarding_domain.di

import com.ashrafovtaghi.onboarding_domain.usecase.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingDomainModule {

    @Provides
    @Singleton
    fun provideValidateNutrients(): ValidateNutrients {
        return ValidateNutrients()
    }
}