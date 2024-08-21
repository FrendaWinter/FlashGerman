package com.german.flip

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CommonWordDao {

    // suspend: Wait for operation finish
    @Upsert
    suspend fun insertWord(word: CommonWord)

    @Delete
    suspend fun deleteWord(word: CommonWord)

    @Query("Select * from commonword order by id asc")
    fun getWordOrderById(): Flow<List<CommonWord>>

    @Query("Select * from commonword order by germanWord asc")
    fun getWordOrderByAlphabet(): Flow<List<CommonWord>>
}