package com.example.valentinesgarage.ui.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.valentinesgarage.R

object SoundManager {

    private var mediaPlayer: MediaPlayer? = null

    fun playStartup(context: Context) {
        play(context, R.raw.startup_sound)
    }

    fun playAction(context: Context) {
        play(context, R.raw.action_sound)
    }

    private fun play(context: Context, resId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}