package co.redheron.hiddenwords.di

import android.content.Context
import co.redheron.hiddenwords.data.WordDataSource
import co.redheron.hiddenwords.data.sqlite.DbHelper
import co.redheron.hiddenwords.data.sqlite.GameDataSQLiteDataSource
import co.redheron.hiddenwords.data.xml.WordXmlDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGameRoundDataSource(dbHelper: DbHelper): GameDataSQLiteDataSource {
        return GameDataSQLiteDataSource(dbHelper)
    }

    @Provides
    @Singleton
    fun provideWordDataSource(@ApplicationContext context: Context): WordDataSource {
        return WordXmlDataSource(context)
    }
}
