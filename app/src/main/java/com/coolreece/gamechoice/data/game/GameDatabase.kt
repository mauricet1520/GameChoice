package com.coolreece.gamechoice.data.game

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "games.db"
                    ).build()
                }
            }

            return INSTANCE!!
        }
    }
}