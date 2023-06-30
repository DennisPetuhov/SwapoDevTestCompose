package com.example.swapidevtest.DATA.DI

import android.content.Context
import androidx.room.Room
import com.example.swapidevtest.DATA.DB.Constants.PERSON_DATABASE
import com.example.swapidevtest.DATA.DB.PersonDatabase
import com.example.swapidevtest.DATA.DB.PersonEntity
import com.example.ui.DATA.Api.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://swapi.dev/api/"

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    @Singleton
    fun provideRetrofit(
//        okHttp: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): PersonDatabase {
        return Room.databaseBuilder(
            context, PersonDatabase::class.java, PERSON_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }




    @Provides
    @Singleton
    fun provideDao(db: PersonDatabase) = db.personDao()
    @Provides
    fun provideEntity() = PersonEntity()
}