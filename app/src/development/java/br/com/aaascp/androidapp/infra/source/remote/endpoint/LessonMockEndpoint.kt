package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import br.com.aaascp.androidapp.util.AndroidUtils
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.mock.Calls


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

        if (AndroidUtils().isOnline()) {
            return delegate
                    .returningResponse(data)
                    .getForArea(lessonFilterRequestBody)
        } else {
            val response: Response<String> = Response.error(
                    401,
                    ResponseBody.create(
                            MediaType.parse(
                                    "application/json"),
                            "{messsage: \"Unauthorized\"}"))
            return delegate
                    .returning(Calls.response(response))
                    .getForArea(areaId!!)
        }

    }
}