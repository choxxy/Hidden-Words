package co.redheron.hiddenwords.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import co.redheron.hiddenwords.ViewModelFactory
import co.redheron.hiddenwords.data.GameThemeRepository
import co.redheron.hiddenwords.data.WordDataSource
import co.redheron.hiddenwords.data.sqlite.DbHelper
import co.redheron.hiddenwords.data.sqlite.GameDataSQLiteDataSource
import co.redheron.hiddenwords.gamehistory.GameHistoryViewModel
import co.redheron.hiddenwords.gameover.GameOverViewModel
import co.redheron.hiddenwords.gameplay.GamePlayViewModel
import co.redheron.hiddenwords.mainmenu.MainMenuViewModel
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
    fun provideViewModelFactory(
        gameDataSource: GameDataSQLiteDataSource,
        wordDataSource: WordDataSource
    ): ViewModelFactory {
        return ViewModelFactory(
            GameOverViewModel(gameDataSource),
            GamePlayViewModel(gameDataSource, wordDataSource),
            MainMenuViewModel(GameThemeRepository()),
            GameHistoryViewModel(gameDataSource)
        )
    }

    @Provides
    @Singleton
    fun provideDbHelper(@ApplicationContext context: Context?): DbHelper {
        return DbHelper(context)
    }
}
