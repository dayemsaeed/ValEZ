package com.lumen.valez_android.di

import com.google.firebase.auth.FirebaseAuth
import com.lumen.valez_android.data.repository.AuthRepository
import com.lumen.valez_android.data.repository.AuthRepositoryImpl
import com.lumen.valez_android.data.service.CarsApi
import com.lumen.valez_android.data.service.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValezModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideCarRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://vpic.nhtsa.dot.gov/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideImageRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://api.carmd.com/v3.0/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCarApiService(retrofit: Retrofit): CarsApi {
        return retrofit.create(CarsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApiService(retrofit: Retrofit): ImageApi {
        return retrofit.create(ImageApi::class.java)
    }

}