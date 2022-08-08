package co.redheron.hiddenwords.gameover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.redheron.hiddenwords.data.sqlite.GameDataSQLiteDataSource
import co.redheron.hiddenwords.model.GameDataInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameOverViewModel @Inject constructor(private val mGameDataSource: GameDataSQLiteDataSource) : ViewModel() {
    private val mOnGameDataInfoLoaded = MutableLiveData<GameDataInfo>()
    fun loadData(gid: Int) {
        mGameDataSource.getGameDataInfo(gid) { value: GameDataInfo -> mOnGameDataInfoLoaded.setValue(value) }
    }

    fun deleteGameRound(gid: Int) {
        mGameDataSource.deleteGameData(gid)
    }

    val onGameDataInfoLoaded: LiveData<GameDataInfo>
        get() = mOnGameDataInfoLoaded
}
