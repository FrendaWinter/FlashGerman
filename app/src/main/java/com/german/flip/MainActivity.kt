package com.german.flip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            CommonWordDB::class.java, "CommonWord"
        ).createFromAsset("database/CommonWord.db").allowMainThreadQueries().build()

        setContent {
            var index by remember { mutableIntStateOf(1) }
            var item = db.dao.getWordById(index)
            var germanWord by remember { mutableStateOf(item.germanWord) }
            var englishWord by remember { mutableStateOf(item.englishWord) }

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FlashCard(germanWord, englishWord)
                NextButton(onClick = {
                    index++
                    println(index)
                    item = db.dao.getWordById(index)
                    germanWord = item.germanWord
                    englishWord = item.englishWord
                })
            }
        }
    }
}

@Composable
fun FlashCard(germanWord: String, englishWord: String) {
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateColor by animateColorAsState(
        targetValue = if (rotated) Color.Red else Color.Blue,
        animationSpec = tween(500), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxSize(.5f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable {
                rotated = !rotated
            },
        colors = CardDefaults.cardColors(containerColor = animateColor)
    )
    {
        Column(
            Modifier
                .fillMaxSize()
                .weight(1f, false),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = if (rotated) englishWord else germanWord,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = if (rotated) animateBack else animateFront
                        rotationY = rotation
                    }
            )
        }
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Favorite Icon",
            modifier = Modifier.size(24.dp), // Set the size of the icon
            tint = Color.White // Change the icon color if needed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FlashCard("Hallo", "Hello")
        Spacer(modifier = Modifier.padding(100 .dp))
        NextButton(onClick = {
            // Handle the button click here
            println("Button Clicked!")
        })
    }
}