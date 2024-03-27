package com.ashrafovtaghi.foodtracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.ashrafovtaghi.core.data.preferences.DefaultPreferences
import com.ashrafovtaghi.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPref: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPref)
    }
}