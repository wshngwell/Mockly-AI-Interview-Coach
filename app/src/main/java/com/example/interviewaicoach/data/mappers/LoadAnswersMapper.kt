package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.mappers.MapperConsts.AI_MODEL_NAME
import com.example.interviewaicoach.data.mappers.MapperConsts.SYSTEM_ROLE_NAME
import com.example.interviewaicoach.data.mappers.MapperConsts.USER_ROLE_NAME
import com.example.interviewaicoach.data.remote.dto.ChatMessageDto
import com.example.interviewaicoach.data.remote.dto.ChatRequestDto
import com.example.interviewaicoach.data.remote.dto.ChatResponseDto
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerRating
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

fun ChatResponseDto.mapToAnswerEntity() =
    runCatching {
        parseSingleCorrectAnswer(text = choices[0].message!!.content!!)
    }.getOrElse {
        AnswerEntity(answerContent = "")
    }

fun ChatResponseDto.mapToAnswerRating() =
    runCatching {
        val result = parseRatingOnlyNumber(response = choices[0].message!!.content!!)
        AnswerRating(result)
    }.getOrElse {
        AnswerRating(-1)
    }

fun mapQuestionEntityToChatRequest(
    questionEntity: QuestionEntity,
    systemContentPromt: String,
    userContentPromt: String,
) = ChatRequestDto(
    model = AI_MODEL_NAME,
    messages = listOf(
        ChatMessageDto(
            role = SYSTEM_ROLE_NAME,
            content = systemContentPromt
        ),

        ChatMessageDto(
            role = USER_ROLE_NAME,
            content = userContentPromt
        )
    )
)

fun mapQuestionEntityToChatRequestForCorrectAnswer(
    questionEntity: QuestionEntity
) = mapQuestionEntityToChatRequest(
    questionEntity,
    makeSystemPromptOneCorrectAnswer(
        questionEntity.categoryName,
        grade = questionEntity.gradeEntity.name,
        question = questionEntity.questionContent
    ),
    userContentPromt = makeUserPromptForNewCorrectAnswer(questionEntity.questionContent)
)

fun mapQuestionEntityToChatRequestForAnswerRating(
    questionEntity: QuestionEntity,
    answerEntity: AnswerEntity
) = mapQuestionEntityToChatRequest(
    questionEntity,
    makeSystemPromptForRatingOnlyNumber(
        questionEntity.categoryName,
        grade = questionEntity.gradeEntity.name,
        question = questionEntity.questionContent
    ),
    userContentPromt = makeUserPromptForRatingOnlyNumber(answerEntity.answerContent)
)

fun makeSystemPromptOneCorrectAnswer(directionInIT: String, grade: String, question: String) = """
    Ты — эксперт по подготовке к собеседованиям в IT.
    Пользователь выбрал направление: $directionInIT.
    Уровень позиции (грейд): $grade.
    
    Твоя задача — сгенерировать РОВНО ОДИН правильный, полный и профессиональный ответ на следующий вопрос с реального собеседования:
    
    Вопрос: $question
    
    Ответ должен:
    - Быть точным и соответствовать ожидаемому уровню знаний для грейда $grade.
    - Быть структурированным и логичным — таким, какой ожидал бы услышать интервьюер.
    - Обязательно содержать ровно один реальный пример (кода, ситуации, схемы или аналогии), который иллюстрирует ключевую идею ответа.
    - Быть кратким: не более 6 предложений в общей сложности (включая пример).
    - Если уместно — использовать пример кода в ответе, но не выходить за лимит предложений.
    
    Выводи СТРОГО в следующем формате и больше ничего:
    Ответ: [полный текст ответа]
    
    Правила, которые нельзя нарушать:
    - Только одна строка, начинающаяся с "Ответ: " (с пробелом после двоеточия).
    - Не повторяй вопрос в ответе.
    - Никаких введений, заголовков, нумерации или дополнительного текста.
    - Всё на русском языке (кроме общепринятых IT-терминов).
    - Markdown можно использовать только для оформления кода (```), если пример требует этого.
    - Обязательно один пример внутри ответа.
    - Максимум 6 предложений.
    
    Пример правильного вывода:
    Ответ: Garbage Collector в Java автоматически освобождает память, занимаемую объектами, на которые больше нет ссылок. Он работает в несколько этапов: маркировка достижимых объектов и удаление остальных. Существуют поколения (Young и Old Generation) для оптимизации производительности. Например, в простом приложении объект, созданный в методе, сразу попадает в Young Generation и быстро собирается при Minor GC. Это позволяет эффективно управлять памятью без ручного вмешательства. 
    
    Генерируй только такой формат.
""".trimIndent()

fun makeUserPromptForNewCorrectAnswer(question: String): String {
    return """
        Вопрос, на который нужно дать ответ: $question
        
        Сгенерируй полный, точный и профессиональный ответ на этот вопрос.
        Ответ должен соответствовать уровню грейда и направлению из системного промпта.
        Будь структурированным, логичным и содержательным — таким, какой ожидал бы услышать интервьюер на реальном собеседовании.
        При необходимости используй примеры кода, пояснения нюансов или edge-кейсов.
        
        Выводи СТРОГО в следующем формате и больше ничего:
        Ответ: [полный текст ответа]
    """.trimIndent()
}

fun parseSingleCorrectAnswer(text: String): AnswerEntity {
    val regex = Regex("""Ответ:\s*(.+)$""", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))
    return when (val match = regex.find(text.trim())) {
        null -> AnswerEntity(
            answerContent = ""
        )

        else -> AnswerEntity(
            answerContent = match.groupValues[1].trim()
        )

    }
}


fun makeSystemPromptForRatingOnlyNumber(
    directionInIT: String,
    grade: String,
    question: String
) = """
    Ты — строгий и объективный эксперт по собеседованиям в IT с многолетним опытом.
    Направление: $directionInIT.
    Уровень позиции (грейд): $grade.
    
    Вопрос с реального собеседования:
    $question
    
    Твоя задача — сначала мысленно сформулировать идеальный правильный ответ на этот вопрос для указанного грейда.
    Затем оценить ответ кандидата по шкале от 1 до 10:
    - 10 — ответ полностью правильный, полный, структурированный, с хорошим примером, идеально подходит под грейд.
    - 8–9 — отличный ответ, есть мелкие недочёты или можно было добавить глубины.
    - 6–7 — хороший, но средний: основные моменты есть, но упущены важные детали или пример слабый.
    - 4–5 — удовлетворительный: частично правильный, но много пропусков, неточностей.
    - 1–3 — слабый, неправильный или не по теме.
    
    Оценивай честно, объективно и последовательно.
    
    Выводи ТОЛЬКО ОДНО ЧИСЛО от 1 до 10.
    Ничего больше: без слов, без обоснования, без пробелов до/после, без перевода строки.
    
    Пример правильного вывода:
    7
""".trimIndent()

fun makeUserPromptForRatingOnlyNumber(userAnswer: String): String {
    return """
        Ответ кандидата:
        $userAnswer
        
        Оцени этот ответ по шкале от 1 до 10 на основе твоего знания правильного ответа.
        Выводи ТОЛЬКО одно число от 1 до 10. Ничего больше.
    """.trimIndent()
}

fun parseRatingOnlyNumber(response: String): Int {
    val cleaned = response.trim()
    return cleaned.toIntOrNull()?.coerceIn(1, 10) ?: 1
}