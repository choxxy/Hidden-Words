package co.redheron.hiddenwords.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import co.redheron.hiddenwords.data.sqlite.DbHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("word-search", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDbHelper(@ApplicationContext context: Context?): DbHelper {
        return DbHelper(context)
    }
}
