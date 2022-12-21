package co.africanwolf.hiddenwords.mainmenu

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.africanwolf.hiddenwords.FullscreenActivity
import co.africanwolf.hiddenwords.R
import co.africanwolf.hiddenwords.data.entity.Game
import co.africanwolf.hiddenwords.data.entity.GameLevel
import co.africanwolf.hiddenwords.data.entity.GameType
import co.africanwolf.hiddenwords.databinding.ActivityMainMenuBinding
import co.africanwolf.hiddenwords.easyadapter.MenuItemAdapter
import co.africanwolf.hiddenwords.gameplay.GamePlayActivity
import co.africanwolf.hiddenwords.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainMenuActivity : FullscreenActivity() {
    private val mViewModel: MainMenuViewModel by viewModels()
    private lateinit var mAdapter: MenuItemAdapter
    private lateinit var binding: ActivityMainMenuBinding
    private var mGameLevel = GameLevel.EASY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLevelPicker()

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
            gameList.add(Game(id = i, type = games[i], color = colors[i]))
        }
        return gameList
    }

    private fun setupLevelPicker() {
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_item,
            GameLevel.values().map {
                it.levelName
            }
        )

        binding.gameLevelPicker.setAdapter(adapter)

        binding.gameLevelPicker.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                mGameLevel = GameLevel.values()[position]
            }

        binding.gameLevelPicker.setText(GameLevel.EASY.levelName, false)
    }

    fun newGame(game: Game) {
        val mGameRoundDimVals = resources.getIntArray(R.array.game_grid_size_values)
        val dim = when (mGameLevel) {
            GameLevel.EASY -> {
                mGameRoundDimVals[GameLevel.EASY.ordinal]
            }
            GameLevel.MEDIUM -> {
                mGameRoundDimVals[GameLevel.MEDIUM.ordinal]
            }
            GameLevel.HARD -> {
                mGameRoundDimVals[GameLevel.HARD.ordinal]
            }
            GameLevel.PRO -> {
                mGameRoundDimVals[GameLevel.PRO.ordinal]
            }
        }

        val intent = Intent(this@MainMenuActivity, GamePlayActivity::class.java)
        intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, dim)
        intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, dim)
        intent.putExtra(
            GamePlayActivity.EXTRA_GAME_DATA,
            Json.encodeToString(
                Game.serializer(),
                game.apply {
                    gameLevel = mGameLevel
                }
            )
        )
        startActivity(intent)
    }
}
