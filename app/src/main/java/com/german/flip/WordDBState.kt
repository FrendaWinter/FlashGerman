package com.german.flip

data class WordDBState (
    val words: List<CommonWord> = emptyList(),
    val germanWord: String = "",
    val englishWord: String = "",
    val type: String = "",
    val germanSentence: String = "",
    val englishSentence: String = "",

    val isAddingNewWord: Boolean = false,
    val sortType: SortType = SortType.Id
)