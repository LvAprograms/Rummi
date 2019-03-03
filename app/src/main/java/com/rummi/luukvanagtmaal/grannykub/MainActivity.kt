package com.rummi.luukvanagtmaal.grannykub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsBtn = findViewById<Button>(R.id.settingsBtn)
        val playBtn = findViewById<Button>(R.id.playBtn)

        settingsBtn.setOnClickListener {
            onClick(settingsBtn)
        }
        playBtn.setOnClickListener {
            onClick(playBtn)
        }

    }

    override fun onClick(view: View) {
            when (view.id) {
                R.id.settingsBtn -> {
                    val startIntent = Intent(applicationContext, SettingsActivity::class.java)
                    startActivity(startIntent)
                }
                R.id.playBtn -> {
                    val startPlayIntent = Intent(applicationContext, PlayActivity::class.java)
                    startActivity(startPlayIntent)
                }
            }

    }
}
