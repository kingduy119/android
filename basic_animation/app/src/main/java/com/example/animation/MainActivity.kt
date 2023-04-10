package com.example.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.animation.data.CardsAdapter
import com.example.animation.data.cards

class MainActivity : AppCompatActivity() {
    lateinit var animation: Animation
    lateinit var btnReset: Button
    private lateinit var rvCards: RecyclerView
    lateinit var cardsAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardsAdapter = CardsAdapter(cards)
        btnReset = findViewById(R.id.btnReset)
        rvCards = findViewById(R.id.rvCards)
        rvCards.adapter = cardsAdapter
        rvCards.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        btnReset.setOnClickListener {
            rvCards.startLayoutAnimation()
        }
    }
}


