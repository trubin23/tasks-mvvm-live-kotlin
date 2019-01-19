package ru.trubin23.tasks_mvvm_live_kotlin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task @JvmOverloads constructor(
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "description") var description: String = "",
        @PrimaryKey @ColumnInfo(name = "entryId") var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "completed") var isCompleted: Boolean = false
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) {
            title
        } else {
            description
        }

    val isActive: Boolean
        get() = !isCompleted

    val isEmpty: Boolean
        get() = title.isEmpty() && description.isEmpty()
}