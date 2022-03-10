package com.xayah.gitforandroid.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class RepoDataBase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
