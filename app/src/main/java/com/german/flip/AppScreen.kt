package com.german.flip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppScreen(
    state: WordDBState,
    onEvent: (UserEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(UserEvent.ShowPopup)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Word"
                )
            }
        }
    ) {
        padding -> LazyColumn (
            contentPadding =  padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        )
        {
            item { Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SortType.values().forEach { sortType -> Row (
                    modifier = Modifier.clickable() {
                        onEvent(UserEvent.SortWords(sortType))
                    },
                    verticalAlignment = CenterVertically
                ) {
                    RadioButton (
                        selected = state.sortType == sortType,
                        onClick = {
                            onEvent(UserEvent.SortWords(sortType))
                        }
                    )
                    Text(text = sortType.name)
                } }
            } }
            items(state.words) { word ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${word.germanWord} ${word.englishWord}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = word.type,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${word.germanSentence} ${word.englishSentence}",
                            fontSize = 20.sp
                        )
                    }
                    IconButton(onClick = {
                        onEvent(UserEvent.DeleteWord(word))
                    }) {
                        Icon (
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete word"
                        )
                    }
                }
            }
        }
    }
}