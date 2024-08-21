package com.german.flip

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class WordsViewModel(
    private val dao: CommonWordDao
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.Id)
    private val _state = MutableStateFlow(WordDBState())
}