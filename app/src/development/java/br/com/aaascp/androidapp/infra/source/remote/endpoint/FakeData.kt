package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.AreaResponse
import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import br.com.aaascp.androidapp.infra.source.remote.body.area.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonResponseBody
import java.util.*

class FakeData {

    private val areasBody = listOf(
            AreaResponseBody(UUID.randomUUID().toString(), "Fisica"),
            AreaResponseBody(UUID.randomUUID().toString(), "Matematica"),
            AreaResponseBody(UUID.randomUUID().toString(), "Biologia"),
            AreaResponseBody(UUID.randomUUID().toString(), "Quimica"),
            AreaResponseBody(UUID.randomUUID().toString(), "Historia")
    )

    val areas = AreaResponse(areasBody)

    fun getLessons(start: Int, end: Int) =
            LessonResponse(
                    listOf(
                            physicsLesson("Cinematica"),
                            physicsLesson("Dinamica"),
                            physicsLesson("Optica"),
                            physicsLesson("Ondulatoria"),
                            physicsLesson("Quantica"),
                            physicsLesson("Cinematica Avancada"),
                            physicsLesson("Dinamica Avancada"),
                            physicsLesson("Optica Avancada"),
                            physicsLesson("Ondulatoria Avancada"),
                            physicsLesson("Quantica Avancada"),
                            physicsLesson("Cinematica Avancada"),
                            physicsLesson("Dinamica Avancada"),
                            physicsLesson("Optica Avancada"),
                            physicsLesson("Ondulatoria Avancada"),
                            physicsLesson("Quantica Avancada"),
                            mathLesson("Geometria"),
                            mathLesson("Calculo A"),
                            mathLesson("Calculo B"),
                            mathLesson("Algebra"),
                            mathLesson("Algebra Linear"),
                            biologyLesson("Botanica"),
                            biologyLesson("Ecologia"),
                            biologyLesson("Genetica"),
                            biologyLesson("Bioquimica"),
                            biologyLesson("Microbiologia"),
                            chemistryLesson("Organica"),
                            chemistryLesson("Inorganica"),
                            chemistryLesson("Ambiental"),
                            chemistryLesson("Introducao"),
                            chemistryLesson("Fisico-Quimica"),
                            historyLesson("Geral"),
                            historyLesson("Brasil"),
                            historyLesson("Santa Catarina")
                    ).subList(start,end))

    private fun lesson(area: AreaResponseBody, title: String): LessonResponseBody {
        return LessonResponseBody(
                UUID.randomUUID().toString(),
                area,
                title)
    }

    private fun physicsLesson(title: String): LessonResponseBody {
        return lesson(areasBody[0], title)
    }

    private fun mathLesson(title: String): LessonResponseBody {
        return lesson(areasBody[1], title)
    }

    private fun biologyLesson(title: String): LessonResponseBody {
        return lesson(areasBody[2], title)
    }

    private fun chemistryLesson(title: String): LessonResponseBody {
        return lesson(areasBody[3], title)
    }

    private fun historyLesson(title: String): LessonResponseBody {
        return lesson(areasBody[3], title)
    }
}

