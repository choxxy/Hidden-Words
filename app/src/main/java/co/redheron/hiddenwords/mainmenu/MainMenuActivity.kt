package co.redheron.hiddenwords.mainmenu

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import co.redheron.hiddenwords.FullscreenActivity
import co.redheron.hiddenwords.R
import co.redheron.hiddenwords.data.entity.Game
import co.redheron.hiddenwords.data.entity.GameType
import co.redheron.hiddenwords.databinding.ActivityMainMenuBinding
import co.redheron.hiddenwords.easyadapter.MenuItemAdapter
import co.redheron.hiddenwords.gameplay.GamePlayActivity
import co.redheron.hiddenwords.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainMenuActivity : FullscreenActivity() {
    private val mViewModel: MainMenuViewModel by viewModels()
    private lateinit var mAdapter: MenuItemAdapter
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = MenuItemAdapter(object : MenuItemAdapter.OnClickListener {
            override fun onClick(game: Game) {
                newGame(game)
            }
        })
        binding.list.adapter = mAdapter
        binding.list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter.submitList(menuItems())
        mViewModel.loadData()

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun menuItems(): List<Game> {
        val colors = resources.getStringArray(R.array.menu_colors)
        val games = GameType.values()
        val gameList: MutableList<Game> = ArrayList()
        for (i in games.indices) {
            gameList.add(Game(i, games[i], colors[i], 100))
        }
        return gameList
    }

    fun newGame(game: Game) {

        val mGameRoundDimVals = resources.getIntArray(R.array.game_round_dimension_values)
        val dim = mGameRoundDimVals[(Math.random() * mGameRoundDimVals.size).toInt()]
        val intent = Intent(this@MainMenuActivity, GamePlayActivity::class.java)
        intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, dim)
        intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, dim)
        intent.putExtra(
            GamePlayActivity.EXTRA_GAME_DATA,
            Json.encodeToString(Game.serializer(), game)
        )
        startActivity(intent)
    }
}
