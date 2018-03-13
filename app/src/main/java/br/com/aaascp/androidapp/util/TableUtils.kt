package br.com.aaascp.androidapp.util

import android.content.Context
import br.com.aaascp.androidapp.BuildConfig
import br.com.aaascp.androidapp.MainApplication
import java.util.Date

class TableUtils {

    companion object {
        private const val FILE_KEY = BuildConfig.APPLICATION_ID
        private const val AREA_TABLE_LAST_UPDATE = "AREA_TABLE_LAST_UPDATE"
        private const val LESSON_TABLE_LAST_UPDATE = "LESSON_TABLE_LAST_UPDATE"
    }

    private val context: Context = MainApplication.component.getApplicationContext()

    private val sharedPref =  context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)

    fun getAreaTableLastUpdate(): String? {
        return getTableLastUpdate(AREA_TABLE_LAST_UPDATE)
    }

    fun setAreaTableLastUpdate(date: Date = Date()) {
        setTableLastUpdate(AREA_TABLE_LAST_UPDATE, date)
    }

    fun getLessonTableLastUpdate(): String? {
        return getTableLastUpdate(LESSON_TABLE_LAST_UPDATE)
    }

    fun setLessonTableLastUpdate(date: Date = Date()) {
        setTableLastUpdate(LESSON_TABLE_LAST_UPDATE, date)
    }

    private fun getTableLastUpdate(table: String): String?{
        return sharedPref.getString(table, null)
    }

    private fun setTableLastUpdate(table: String, date: Date) {
        val editor = sharedPref.edit()
        editor.putString(table, DateFormatterUtil.dateTimeFormatInstance.format(date))
        editor.apply()
    }
}