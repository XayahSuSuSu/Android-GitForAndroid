package com.xayah.gitforandroid.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "repo_name") val repoName: String,
    @ColumnInfo(name = "repo_author") val repoAuthor: String,
    @ColumnInfo(name = "repo_path") val repoPath: String,
    @ColumnInfo(name = "current_branch") val currentBranch: String,
)