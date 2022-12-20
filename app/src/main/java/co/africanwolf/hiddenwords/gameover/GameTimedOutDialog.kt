package co.africanwolf.hiddenwords.gameover

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import co.africanwolf.hiddenwords.R
import co.africanwolf.hiddenwords.databinding.ViewGameTimeoutDialogBinding
import co.africanwolf.hiddenwords.model.GameDataInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameTimedOutDialog(private val gameId: Int, private val dialogCallback: (String) -> Unit) : DialogFragment() {
    private var binding: ViewGameTimeoutDialogBinding? = null
    private val mViewModel: GameOverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewGameTimeoutDialogBinding.inflate(inflater, container, false)

        binding?.next?.setOnClickListener {
            dialogCallback(ACTION_NEXT_GAME)
            dismiss()
        }

        binding?.mainMenu?.setOnClickListener {
            dialogCallback(ACTION_MAIN_MENU)
            dismiss()
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.loadData(gameId)
        mViewModel.onGameDataInfoLoaded.observe(this) { info: GameDataInfo -> showGameStat(info) }
    }

    private fun showGameStat(info: GameDataInfo) {}

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
}
