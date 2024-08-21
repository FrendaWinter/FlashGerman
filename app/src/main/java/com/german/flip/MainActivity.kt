package com.german.flip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var frontAnimation:AnimatorSet
    private lateinit var backAnimation:AnimatorSet
    private var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Now Create Animator Object
        // For this we add animator folder inside res
        // Now we will add the animator to our card
        // we now need to modify the camera scale
        val scale = applicationContext.resources.displayMetrics.density

        val front = findViewById<TextView>(R.id.card_front) as TextView
        val back = findViewById<TextView>(R.id.card_back) as TextView
        val flip = findViewById<Button>(R.id.flip_btn) as Button

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale


        // Now we will set the front animation
        frontAnimation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        backAnimation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        // Now we will set the event listener
        flip.setOnClickListener{
            if(isFront)
            {
                frontAnimation.setTarget(front)
                backAnimation.setTarget(back)
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
    }
}