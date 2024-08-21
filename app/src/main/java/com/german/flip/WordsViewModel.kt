package com.german.flip

import android.provider.UserDictionary.Words
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WordsViewModel(
    private val dao: CommonWordDao
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.Id)
    private val _words = _sortType.flatMapLatest {
        sortType -> when(sortType) {
            SortType.Id -> dao.getWordOrderById()
            SortType.Alphabet -> dao.getWordOrderByAlphabet()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(WordDBState())
    val state = combine(_state, _sortType, _words) {
        state, sortType, words -> state.copy(
            words = words,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WordDBState())

    fun onEvent(event: UserEvent) {
        when(event){
            UserEvent.AddNewWord -> {
                val germanWord = state.value.germanWord
                val englishWord = state.value.englishWord
                val type = state.value.type
                val germanSentence = state.value.germanSentence
                val englishSentence = state.value.englishSentence

                if (germanWord.isBlank() || englishWord.isBlank() || type.isBlank() || germanSentence.isBlank() || englishSentence.isBlank()) return

                val word = CommonWord(
                    germanWord = germanWord,
                    englishWord = englishWord,
                    type = type,
                    germanSentence = germanSentence,
                    englishSentence = englishSentence
                )

                viewModelScope.launch { dao.insertWord(word) }
                _state.update { it.copy(
                    isAddingNewWord = false,
                    germanWord = "",
                    englishWord = "",
                    type = "",
                    germanSentence = "",
                    englishSentence = ""
                ) }
            }

            is UserEvent.DeleteWord -> {
                viewModelScope.launch {
                    dao.deleteWord(event.word)
                }
            }

            is UserEvent.SetGermanWord -> {
                _state.update { it.copy(
                    germanWord = event.germanWord
                ) }
            }
            is UserEvent.SetEnglishWord -> {
                _state.update { it.copy(
                    englishWord = event.englishWord
                ) }
            }
            is UserEvent.SetGermanSentence -> {
                _state.update { it.copy(
                    germanSentence = event.germanSentence
                ) }
            }
            is UserEvent.SetEnglishSentence -> {
                _state.update { it.copy(
                    englishSentence = event.englishSentence
                ) }
            }
            is UserEvent.SetWordType -> {
                _state.update { it.copy(
                    type = event.wordType
                ) }
            }
            is UserEvent.SortWords -> {
                _sortType.value = event.sortType
            }

            UserEvent.ShowPopup -> {
                _state.update { it.copy(
                    isAddingNewWord = true
                ) }
            }
            UserEvent.HidePopup -> {
                _state.update { it.copy(
                    isAddingNewWord = false
                ) }
            }
        }
    }
}