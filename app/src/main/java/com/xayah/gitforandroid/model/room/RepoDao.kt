package com.xayah.gitforandroid.model.room

import androidx.room.*

@Dao
interface RepoDao {
    @Insert
    fun insert(vararg repoEntity: RepoEntity)

    @Delete
    fun delete(repoEntity: RepoEntity)

    @Update
    fun update(repoEntity: RepoEntity)

    @Query("SELECT * FROM repoEntity")
    fun getAll(): List<RepoEntity>
}