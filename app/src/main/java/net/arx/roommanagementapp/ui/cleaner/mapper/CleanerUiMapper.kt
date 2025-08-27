package net.arx.roommanagementapp.ui.cleaner.mapper

import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import javax.inject.Inject

class CleanerUiMapper @Inject constructor(){

    operator fun invoke(): List<CleanerUiItem> {

        return emptyList()
    }

}