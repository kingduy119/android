package com.example.helloworld

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class ContentProviderActivity : AppCompatActivity() {
    private lateinit var btnMainActivity: Button
    private lateinit var btnServiceActivity: Button

    private lateinit var btnBroadcast: Button
    private lateinit var btnSendData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
        title = "Content Provider Activity"

        btnMainActivity = findViewById(R.id.btnMain)
        btnServiceActivity = findViewById(R.id.btnService)
        btnBroadcast = findViewById(R.id.btnBroadcast)
        btnSendData = findViewById(R.id.btnSendData)

        // Move to activity
        btnMainActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnServiceActivity.setOnClickListener {
            val intent = Intent(this, ServiceActivity::class.java)
            startActivity(intent)
        }

        // send broadcast to main activity
        btnBroadcast.setOnClickListener {
            val intent = Intent("com.example.helloworld.CUSTOM_INTENT")
            sendBroadcast(intent)
        }
        // send data
        btnSendData.setOnClickListener {
            var intent = Intent(this, MyService::class.java)
            intent.putExtra("MyServiceKey", "hello")
            startService(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ContentProviderActivity", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ContentProviderActivity", "onStop")
    }

}
