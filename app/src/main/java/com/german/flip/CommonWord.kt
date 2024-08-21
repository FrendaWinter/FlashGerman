package com.german.flip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommonWord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val germanWord: String,
    val englishWord: String,
    val type: String,
    val germanSentence: String,
    val englishSentence: String
)
