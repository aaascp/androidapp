package br.com.aaascp.androidapp.infra.source.remote.body.lesson

data class LessonFilterRequestBody(
        private val value: String,
        private val modifier: String = "Igual",
        private val type: String = "id") {

    fun serialize(): String {
        return "{\"area\":{" +
                "\"value\":\"$value\"," +
                "\"modifier\":\"$modifier\"," +
                "\"type\":\"$type\"}}"
    }
}