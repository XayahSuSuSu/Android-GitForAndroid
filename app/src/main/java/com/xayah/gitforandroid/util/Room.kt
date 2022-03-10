package com.xayah.gitforandroid.util

import android.content.Context
import androidx.room.Room
import com.xayah.gitforandroid.model.room.RepoDataBase
import com.xayah.gitforandroid.model.room.RepoEntity

class Room(context: Context) {
    private var db: RepoDataBase = Room.databaseBuilder(
        context,
        RepoDataBase::class.java, "repo"
    ).build()

    fun insert(repoEntity: RepoEntity) {
        db.repoDao().insert(repoEntity)
    }

    fun delete(repoEntity: RepoEntity) {
        db.repoDao().delete(repoEntity)
    }

    fun update(repoEntity: RepoEntity) {
        db.repoDao().update(repoEntity)
    }

    fun getAll(repoEntity: RepoEntity): List<RepoEntity> {
        return db.repoDao().getAll()
    }
}