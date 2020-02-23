package com.example.currentnetwork

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val REQUEST_CODE_ACCESSIBILITY = 1
        val REQUEST_CODE_OVERLAY_PERMISSION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
    }

    private fun initData() {
        switch1.isChecked = NetworkAccessibilityService.isStart()
        switch1.setOnCheckedChangeListener { _, isChecked ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!switch2.isChecked) {
                    switch1.isChecked = false
                    Toast.makeText(this, "先开启悬浮窗权限", Toast.LENGTH_SHORT).show()
                    return@setOnCheckedChangeListener
                }
            }

            if (isChecked) {
                try {
                    startActivityForResult(
                        Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS),
                        REQUEST_CODE_ACCESSIBILITY
                    )
                } catch (e: Exception) {
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch2.isChecked = Settings.canDrawOverlays(applicationContext)
            switch2.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val intent =
                        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    intent.data = Uri.parse("package:$packageName")
                    startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
                }
            }
        } else {
            switch2.visibility = View.GONE
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_CODE_ACCESSIBILITY) {
            switch1.isChecked = NetworkAccessibilityService.isStart()
        } else if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                switch2.isChecked = Settings.canDrawOverlays(applicationContext)
            }
        }
        if (switch1.isChecked and switch2.isChecked) {
            moveTaskToBack(true)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
