package dev.sasikanth.core.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onStart() {
        super.onStart()
        updateNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    /**
     * Replace @property[mode] and implement preferences check
     */
    private fun updateNightMode(mode: Int) {
        when (mode) {
            AppCompatDelegate.MODE_NIGHT_YES -> delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.MODE_NIGHT_NO -> delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
