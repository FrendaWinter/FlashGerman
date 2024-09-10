package com.german.flip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.sp
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu Icon",
                            modifier = Modifier.size(48.dp), // Custom size
                            tint = Color.White // Custom color
                        )
                    }
                    Button(
                        onClick = {  }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings Icon",
                            modifier = Modifier.size(48.dp), // Set the size of the icon
                            tint = Color.White // Change the icon color if needed
                        )
                    }
                }
                Column (
                    Modifier.fillMaxHeight(.75f)
                ) {
                    FlashCard(germanWord, englishWord)
                }

                Spacer(modifier = Modifier.padding(20.dp))
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

    // State to track whether the card is expanded or not
    var isExpanded by remember { mutableStateOf(false) }

    // Animate the card's height based on the expanded state
    val cardHeight by animateFloatAsState(
        targetValue = if (isExpanded) 1f else .5f,
        label = "" // Change these values as needed
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(.75f)
            .fillMaxHeight(cardHeight)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable {
                rotated = !rotated
            }
        ,
        colors = CardDefaults.cardColors(containerColor = animateColor)
    )
    {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier.fillMaxHeight(.75f),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = if (rotated) englishWord else germanWord,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = if (rotated) animateBack else animateFront
                            rotationY = rotation
                        }
                        .padding(50.dp),
                    fontSize = 30.sp
                )
            }

            if (rotated) {
                Button(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationY = rotation
                        },
                    onClick = {
                        isExpanded = !isExpanded
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Settings Icon",
                        modifier = Modifier.size(48.dp),
                        tint = Color.White
                    )
                }
            }
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
            modifier = Modifier.size(48.dp),
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            Modifier
                .fillMaxWidth()
                .padding(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Icon",
                    modifier = Modifier.size(48.dp), // Custom size
                    tint = Color.White // Custom color
                )
            }
            Button(
                onClick = {  }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings Icon",
                    modifier = Modifier.size(48.dp), // Set the size of the icon
                    tint = Color.White // Change the icon color if needed
                )
            }
        }
        Column (
            Modifier.fillMaxHeight(.75f)
        ) {
            FlashCard("Hallo", "Hello")
        }

        Spacer(modifier = Modifier.padding(20.dp))
        NextButton(onClick = {
            // Handle the button click here
            println("Button Clicked!")
        })
    }
}