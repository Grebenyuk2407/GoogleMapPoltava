package com.example.googlemappoltava

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    @Provides
    fun provideMyViewModel(): MyViewModel {
        return MyViewModel()
    }

    @Provides
    fun provideApiInterface(): ApiInterface {
        return Client.client.create(ApiInterface::class.java)
    }
}