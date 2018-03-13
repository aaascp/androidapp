package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

class LessonMockEndpoint(
        private val delegate: BehaviorDelegate<LessonEndpoint>
) : LessonEndpoint {

    companion object {
        const val NETWORK_PAGE_SIZE: Int = 5
    }

    override fun getForArea(
            lessonFilterRequestBody: String,
            start: Int,
            end: Int
    ): Call<LessonResponse> {

        val data = FakeData().getLessonsForArea("1", start, end)

        return delegate
                .returningResponse(data)
                .getForArea(lessonFilterRequestBody)
    }
}