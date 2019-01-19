package ru.trubin23.tasks_mvvm_live_kotlin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task @JvmOverloads constructor(
        @ColumnInfo(name = "title") var mTitle: String = "",
        @ColumnInfo(name = "description") var mDescription: String = "",
        @PrimaryKey @ColumnInfo(name = "entryId") var mId: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "completed") var mIsCompleted: Boolean = false
) {

    val titleForList: String
        get() = if (mTitle.isNotEmpty()) {
            mTitle
        } else {
            mDescription
        }

    val isActive: Boolean
        get() = !mIsCompleted

    val isEmpty: Boolean
        get() = mTitle.isEmpty() && mDescription.isEmpty()
}