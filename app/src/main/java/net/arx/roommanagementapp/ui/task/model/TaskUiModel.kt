package net.arx.roommanagementapp.ui.task.model

import net.arx.roommanagementapp.framework.db.entity.CleaningType

data class TaskUiItem(
    val id: Long = 0,
    val dayStart: Long = 0,
    val dayEnd: Long = 0,
    val roomId: Long = 0,
    val userId: Long = 0,
    val cleaningType: CleaningType = CleaningType.CLEANED
)
