package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import br.com.aaascp.androidapp.util.AndroidUtils
import retrofit2.Call
import retrofit2.Response


class LessonMockEndpoint : LessonEndpoint {

    companion object {
        const val NETWORK_PAGE_SIZE: Int = 5
    }

    override fun getForArea(
            lessonFilterRequestBody: String,
            start: Int,
            end: Int
    ): Call<LessonResponse> {

        return CallMock<LessonResponse>(
                Response.success(FakeData().getLessons(start,end)),
                AndroidUtils().isOnline())
    }
}