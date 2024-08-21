package com.german.flip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommonWord(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val germanWord: String,
    val englishWord: String,
    val type: String,
    val germanSentence: String,
    val englishSentence: String
)
