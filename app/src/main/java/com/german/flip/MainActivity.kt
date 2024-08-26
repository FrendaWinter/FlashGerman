package com.german.flip

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.compose.material3.Text
import android.widget.TextView
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.room.Room

class MainActivity : ComponentActivity() {
    private lateinit var frontAnimation: AnimatorSet
    private lateinit var backAnimation: AnimatorSet
    private var isFront =true

    private var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            CommonWordDB::class.java, "CommonWords"
        ).createFromAsset("database/CommonWord.db").allowMainThreadQueries().build()

        val scale = applicationContext.resources.displayMetrics.density

        val front = findViewById<TextView>(R.id.card_front) as TextView
        val back =findViewById<TextView>(R.id.card_back) as TextView
        val flip = findViewById<Button>(R.id.flip_btn) as Button
        val next = findViewById<Button>(R.id.next_btn) as Button

        val item = db.dao.getWordById(index)
        front.text = item.germanWord
        back.text = item.englishWord

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale

        // Now we will set the front animation
        frontAnimation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        backAnimation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        // Now we will set the event listener
        flip.setOnClickListener{
            if(isFront)
            {
                frontAnimation.setTarget(front);
                backAnimation.setTarget(back);
                frontAnimation.start()
                backAnimation.start()
                isFront = false

            }
            else
            {
                frontAnimation.setTarget(back)
                backAnimation.setTarget(front)
                backAnimation.start()
                frontAnimation.start()
                isFront =true

            }
        }

        next.setOnClickListener{
            index += 1
            val item = db.dao.getWordById(index)
            front.text = item.germanWord
            back.text = item.englishWord
        }
    }
}