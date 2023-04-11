package com.example.helloworld

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

@Suppress("DEPRECATION")
@SuppressLint("UseSwitchCompatOrMaterialCode")
class MainActivity : AppCompatActivity() {
    private lateinit var btnService: Button
    private lateinit var btnContent: Button

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate")
        title = "Broadcast Receiver"

        // Move to Service activity
        btnService = findViewById(R.id.btnService)
        btnService.setOnClickListener {
            val intent = Intent(this, ServiceActivity::class.java)
            startActivity(intent)
        }

        // Move to Content activity
        btnContent = findViewById(R.id.btnContent)
        btnContent.setOnClickListener {
            val intent = Intent(this, ContentProviderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
        val intentBroadcast = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(duyReceiver, intentBroadcast)
    }

    override fun onStop() {
        Log.d("MainActivity", "onStop")
        unregisterReceiver(duyReceiver)
        super.onStop()
    }

    private var duyReceiver: DuyBroadcastReceiver = DuyBroadcastReceiver()
}

class DuyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DuyBroadcastReceiver", "Action: " + intent.action)
        Toast.makeText(context, "Action: " + intent.action, Toast.LENGTH_SHORT).show()
    }
}



