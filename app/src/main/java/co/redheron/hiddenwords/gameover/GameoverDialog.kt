package co.redheron.hiddenwords.gameover

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import co.redheron.hiddenwords.R
import co.redheron.hiddenwords.commons.DurationFormatter
import co.redheron.hiddenwords.databinding.ViewGameEndDialogBinding
import co.redheron.hiddenwords.model.GameDataInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameoverDialog : DialogFragment() {
    private var binding: ViewGameEndDialogBinding? = null
    private var mGameId: Int = 0
    var onInputListener: GameOverDialogInputListener? = null
    private val mViewModel: GameOverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGameId = requireArguments().getInt(GameOverActivity.EXTRA_GAME_ROUND_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewGameEndDialogBinding.inflate(inflater, container, false)

        binding?.next?.setOnClickListener {
            onInputListener?.sendInput(ACTION_NEXT_GAME)
            dismiss()
        }

        binding?.mainMenu?.setOnClickListener {
            onInputListener?.sendInput(ACTION_MAIN_MENU)
            dismiss()
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.loadData(mGameId)
        mViewModel.onGameDataInfoLoaded.observe(this) { info: GameDataInfo -> showGameStat(info) }
    }

    private fun showGameStat(info: GameDataInfo) {
        binding?.message?.text = getString(
            R.string.finish_text, info.usedWordsCount,
            DurationFormatter.fromInteger(info.duration.toLong())
        )
    }

    override fun getTheme(): Int {
        return R.style.RoundedCornersDialog
    }

    override fun onResume() {
        // Store access variables for window and blank point
        val window = dialog!!.window
        val size = Point()
        // Store dimensions of the screen in `size`
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        // Call super onResume after sizing
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onInputListener = activity as GameOverDialogInputListener?
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: " + e.message)
        }
    }

    interface GameOverDialogInputListener {
        fun sendInput(input: String?)
    }

    companion object {
        private val TAG = GameoverDialog::class.java.simpleName
        const val ACTION_NEXT_GAME = "next_game"
        const val ACTION_MAIN_MENU = "main_menu"

        @JvmStatic
        fun newInstance(title: String?): GameoverDialog {
            val frag = GameoverDialog()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
    }
}
