package com.example.szymo.ajjkalamba

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.Thread.sleep

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    private var logged = false


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            this.supportActionBar!!.hide()
        } catch (e: Exception) {}

        setContentView(R.layout.activity_fullscreen)

        val thread = Thread(){
            run{
                getLogged()
                sleep(4000)
                if(!logged){
                    val changePage = Intent(this, LoginActivity::class.java)
                    startActivity(changePage)
                    finish()
                } else {
                    val changePage = Intent(this, MainActivity::class.java)
                    startActivity(changePage)
                    finish()
                }
            }
        }
        thread.start()
    }

    private fun getLogged(){
        val loginShared = getSharedPreferences("com.example.szymo.ajjkalamba.prefs",0)
        logged = loginShared.getBoolean("logged", false)
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
