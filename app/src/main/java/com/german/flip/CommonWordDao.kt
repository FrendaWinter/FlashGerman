package com.german.flip

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommonWordDao {

    // suspend: Wait for operation finish
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: CommonWord)

    @Delete
    suspend fun deleteWord(word: CommonWord)

    @Query("Select * from commonword where id = :id")
    fun getWordById(id: Int): CommonWord

    @Query("Select * from commonword order by id asc")
    fun getWordOrderById(): List<CommonWord>

    @Query("Select * from commonword order by germanWord asc")
    fun getWordOrderByAlphabet(): List<CommonWord>
}