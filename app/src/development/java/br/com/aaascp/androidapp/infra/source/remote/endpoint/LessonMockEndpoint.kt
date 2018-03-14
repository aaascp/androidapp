package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

class LessonMockEndpoint(
        private val delegate: BehaviorDelegate<LessonEndpoint>
) : LessonEndpoint {

    override fun getForArea(
            lessonFilterRequestBody: String,
            start: Int,
            end: Int
    ): Call<LessonResponse> {

        val regex = """.*"value":"(\w+)".*""".toRegex()


        val areaId = regex
                .matchEntire(lessonFilterRequestBody)
                ?.groups
                ?.get(1)
                ?.value


        val data = FakeData().getLessonsForArea(areaId.orEmpty(), start, end)

        return delegate
                .returningResponse(data)
                .getForArea(lessonFilterRequestBody)
    }
}