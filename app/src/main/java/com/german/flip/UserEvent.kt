package com.german.flip

sealed interface UserEvent {
    data object AddNewWord: UserEvent
    object ShowPopup: UserEvent
    object HidePopup: UserEvent

    data class SetGermanWord(val germanWord: String): UserEvent
    data class SetEnglishWord(val englishWord: String): UserEvent
    data class SetGermanSentence(val germanSentence: String): UserEvent
    data class SetEnglishSentence(val englishSentence: String): UserEvent
    data class SetWordType(val wordType: String): UserEvent

    data class DeleteWord(val word: CommonWord): UserEvent

    data class SortWords(val sortType: SortType): UserEvent
}