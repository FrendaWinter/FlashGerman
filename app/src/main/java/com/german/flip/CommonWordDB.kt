package com.german.flip

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CommonWord::class],
    version = 1
)
abstract class CommonWordDB: RoomDatabase() {
    abstract val dao: CommonWordDao
}