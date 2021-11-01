package com.htf.kidzoon.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.htf.kidzoon.Room.Dao.CountryDao
import com.htf.kidzoon.models.Country
import com.htf.kidzoon.utils.Constants


@Database(entities = [Country::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table
                database.execSQL("BEGIN TRANSACTION;")
                database.execSQL(
                    "CREATE TABLE users_new (User)"
                )
                database.execSQL("COMMIT;")
            }
        }


        var INSTANCE: AppDatabase? = null
        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        Constants.DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        //.addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}
