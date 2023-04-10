package com.example.animation.data

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.animation.R

data class Card (
    @DrawableRes val imageId: Int,
    @StringRes val title: Int,
)

class CardsAdapter(private val cards: List<Card>): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val title: TextView
         val image: ImageView
        init {
            image = view.findViewById(R.id.image)
            title = view.findViewById(R.id.title)
            image.setOnClickListener {
                Log.d("CLICK", title.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.card_item, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setBackgroundResource(cards[position].imageId)
        holder.title.setText(cards[position].title)
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}

val cards = listOf(
    Card(R.drawable.koda, R.string.dog_koda),
    Card(R.drawable.lola, R.string.dog_lola),
    Card(R.drawable.nox, R.string.dog_nox),
    Card(R.drawable.moana, R.string.dog_moana),
    Card(R.drawable.leroy, R.string.dog_leroy),
    Card(R.drawable.tzeitel, R.string.dog_tzeitel),
    Card(R.drawable.tzeitel, R.string.dog_tzeitel),
    Card(R.drawable.tzeitel, R.string.dog_tzeitel),
)

