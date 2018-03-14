package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.AreaResponse
import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import br.com.aaascp.androidapp.util.AndroidUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FakeData {

    fun getAreas(start: Int, end: Int): AreaResponse? {
        val responseType = object : TypeToken<AreaResponse>() {}.type
        val response: AreaResponse? =
                Gson().fromJson(
                        readFile("areas"),
                        responseType)

        response?.data?.let {
            val safeEnd = if (it.size < end) it.size else end
            response.data = it.subList(start, safeEnd)
        }

        return response
    }

    fun getLessonsForArea(
            areaId: String,
            start: Int,
            end: Int
    ): LessonResponse? {

        val responseType = object : TypeToken<LessonResponse>() {}.type
        val response: LessonResponse? =
                Gson().fromJson(
                        readFile("lessons"),
                        responseType)

        response?.data?.let {
            val safeEnd = if (it.size < end) it.size else end
            response.data = it.subList(start, safeEnd).filter { it.area.id == areaId }
        }

        return response
    }

    private fun readFile(fileName: String): String? {
        return AndroidUtils()
                .getAssetFile("$fileName.json")
                ?.bufferedReader()
                .use {
                    it?.readText()
                }
    }
}

