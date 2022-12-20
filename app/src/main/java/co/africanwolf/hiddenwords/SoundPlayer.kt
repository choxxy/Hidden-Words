package co.africanwolf.hiddenwords

import android.content.Context
import android.media.SoundPool
import android.util.SparseIntArray
import co.africanwolf.hiddenwords.settings.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SoundPlayer @Inject constructor(
    @ApplicationContext context: Context,
    private val mPreferences: Preferences,
) {
    enum class Sound {
        Correct, Wrong
    }

    private lateinit var mSoundPool: SoundPool
    private lateinit var mSoundPoolMap: SparseIntArray
    fun play(sound: Sound) {
        if (mPreferences.enableSound()) {
            mSoundPool.play(
                mSoundPoolMap[sound.ordinal],
                1.0f,
                1.0f,
                0,
                0,
                1.0f
            )
        }
    }

    private fun init(context: Context) {
        mSoundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .build()
        mSoundPoolMap = SparseIntArray()
        mSoundPoolMap.put(
            Sound.Correct.ordinal,
            mSoundPool.load(context, R.raw.correct, 1)
        )
        mSoundPoolMap.put(
            Sound.Wrong.ordinal,
            mSoundPool.load(context, R.raw.wrong, 1)
        )
    }

    init {
        init(context)
    }
}
