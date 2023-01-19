package uz.gita.broadcastdemo.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import uz.gita.broadcastdemo.*
import uz.gita.broadcastdemo.databinding.ScreenMainBinding


class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val adapter: MyAdapter by lazy { MyAdapter() }
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.moreBtn.setOnClickListener {
            val powerMenu = PowerMenu.Builder(requireContext())
                .addItem(PowerMenuItem("Sharing", R.drawable.ic_baseline_share_24))
                .addItem(PowerMenuItem("Rating", R.drawable.ic_round_thumb_up_24))
                .setAnimation(MenuAnimation.DROP_DOWN)
                .setMenuRadius(15f)
                .setMenuShadow(10f)
                .setTextColor(Color.parseColor("#006262"))
                .setSelectedTextColor(Color.WHITE)
                .setSelectedMenuColor(Color.parseColor("#006262"))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setLifecycleOwner(viewLifecycleOwner)
                .build()
            powerMenu.showAsDropDown(binding.moreBtn)
        }


        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener {
            viewModel.update(EventEntity(it.id, it.image, it.name, !it.status))
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

    private val onMenuItemClickListener: OnMenuItemClickListener<PowerMenuItem?> =
        OnMenuItemClickListener<PowerMenuItem?> { position, item ->
            when (position) {
                0 -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=${activity?.packageName}")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)

                }
                1 -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=${activity?.packageName}")
                            )
                        )
                    } catch (error: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=${activity?.packageName}")
                            )
                        )
                    }
                }
            }
        }

}