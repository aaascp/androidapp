package br.com.aaascp.androidapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatterUtil private constructor(format: String) {

    private val formatter =
            SimpleDateFormat(
                    format,
                    Locale.getDefault())

    fun format(date: Date): String {
        return formatter.format(date)
    }

    fun parse(dateString: String): Date {
        return formatter.parse(dateString)
    }

    companion object {

        private const val DATE_TIME_FORMAT = "dd/MM/yyyy 'às' HH:mm"

        val dateTimeFormatInstance: DateFormatterUtil
            get() = DateFormatterUtil(DATE_TIME_FORMAT)
    }

}