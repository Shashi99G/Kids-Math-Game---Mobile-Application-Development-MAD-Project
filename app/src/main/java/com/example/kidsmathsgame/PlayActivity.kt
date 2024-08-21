package com.example.kidsmathsgame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
            .setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "+")
            startActivity(calInt)
        }

        .setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "*")
            startActivity(calInt)
        }

        minimum.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "-")
            startActivity(calInt)
        }

        division.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "/")
            startActivity(calInt)
        }
    }
}
