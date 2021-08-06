package com.aumarbello.showcase.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.aumarbello.showcase.R
import com.aumarbello.showcase.databinding.ActivityCarDetailsBinding
import com.aumarbello.showcase.ui.adapters.MediaAdapter
import com.aumarbello.showcase.utils.isVideoUrl
import com.aumarbello.showcase.utils.load
import com.aumarbello.showcase.utils.showSnackBar
import com.aumarbello.showcase.viewmodels.CarDetailsVM
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarDetailsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCarDetailsBinding
    private val viewModel by viewModels<CarDetailsVM>()

    private lateinit var player: SimpleExoPlayer
    private var resumePlayback = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val id = intent.getStringExtra(CAR_ID) ?: return
        viewModel.setCarId(id)

        setObservers()
        setListeners()
    }

    override fun onResume() {
        super.onResume()

        if (resumePlayback) {
            player.play()
        }
    }

    override fun onPause() {
        super.onPause()

        if (::player.isInitialized && player.isPlaying) {
            player.pause()
            resumePlayback = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::player.isInitialized) {
            player.release()
        }
    }

    private fun setObservers() {
        viewModel.details.observe(this) { details ->
            Log.d("Sama", "Set up")
            binding.title.text = details.title
            binding.datePosted.text = createBoldSpan("Posted: ", details.datePosted)
            binding.price.text = createBoldSpan("Price: ", details.price)
            binding.details.location.text = details.location
            binding.details.mileage.text = details.mileage
            binding.details.carImage.load(details.imageUrl)

            binding.details.threeDView.isVisible = details.hasThreeDImage
            binding.details.threeDView.setOnClickListener {
                if (details.threeDImageUrl != null) {
                    try {
                        CustomTabsIntent.Builder()
                            .build()
                            .launchUrl(this, Uri.parse(details.threeDImageUrl))
                    } catch (ex: ActivityNotFoundException) {
                        ex.printStackTrace()
                        showSnackBar("Compatible application not found")
                    }
                }
            }

            val adapter = MediaAdapter {
                binding.details.carImage.isVisible = !it.url.isVideoUrl()
                binding.details.playerView.isVisible = it.url.isVideoUrl()

                if (it.url.isVideoUrl()) {
                    handleVideo(it.url)
                } else {
                    binding.details.carImage.load(it.url)
                }
            }
            adapter.submitList(details.media)
            binding.details.thumbnails.adapter = adapter

            binding.details.information.removeAllViews()
            details.information.forEach { info -> addChip(info) }
        }

        viewModel.getCartInfo().observe(this) {
            binding.cart.count.text = it.toString()
        }

        viewModel.loader.observe(this) {
            binding.loader.isVisible = it
            binding.container.isVisible = !it
        }

        viewModel.error.observe(this) { showSnackBar(it) }
    }

    private fun setListeners() {
        binding.carDetails.setOnClickListener { onBackPressed() }
        binding.back.setOnClickListener { onBackPressed() }
        binding.addToCart.setOnClickListener { viewModel.addToCart() }
    }

    private fun createBoldSpan(label: String, value: String): CharSequence {
        return SpannableStringBuilder(label).apply {
            append(value, StyleSpan(Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
    
    private fun handleVideo(url: String) {
        if (!::player.isInitialized) {
            player = SimpleExoPlayer.Builder(this).build()
            binding.details.playerView.player = player
        }

        val mediaItem = MediaItem.fromUri(url)
        player.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    private fun addChip(info: String) {
        val chip = Chip(this).apply {
            text = info
            textSize = 13f
            chipStartPadding = 20f
            chipEndPadding = 20f

            setTextColor(ResourcesCompat.getColor(resources, R.color.yellow, theme))
            setChipBackgroundColorResource(R.color.purple_700)
        }

        binding.details.information.addView(chip)
    }

    companion object {
        private const val CAR_ID = "car_id"
        fun getStartIntent(context: Context, id: String): Intent {
            return Intent(context, CarDetailsActivity::class.java)
                .putExtra(CAR_ID, id)
        }
    }
}