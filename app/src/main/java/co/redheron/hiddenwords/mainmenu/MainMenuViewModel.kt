package co.redheron.hiddenwords.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.redheron.hiddenwords.data.GameThemeRepository
import co.redheron.hiddenwords.model.GameTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(private val mGameThemeRepository: GameThemeRepository) : ViewModel() {

    private val mOnGameThemeLoaded: MutableLiveData<List<GameTheme>> = MutableLiveData()

    fun loadData() {
        mOnGameThemeLoaded.value = mGameThemeRepository.gameThemes
    }

    val onGameThemeLoaded: LiveData<List<GameTheme>>
        get() = mOnGameThemeLoaded
}
