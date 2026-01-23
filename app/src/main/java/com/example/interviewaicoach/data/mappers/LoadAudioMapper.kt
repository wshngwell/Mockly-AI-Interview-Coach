package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.mappers.MapperConsts.AI_MODEL_NAME_FOR_AUDIO
import com.example.interviewaicoach.data.mappers.MapperConsts.AUDIO_TYPE
import com.example.interviewaicoach.data.mappers.MapperConsts.TEXT_TYPE
import com.example.interviewaicoach.data.mappers.MapperConsts.USER_ROLE_NAME
import com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatMessageForAudioDto
import com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatRequestAudioDto
import com.example.interviewaicoach.data.remote.dto.dtoForAudio.ContentPartDto
import com.example.interviewaicoach.data.remote.dto.dtoForAudio.InputAudioDto

fun String.toChatRequestForAudio() = ChatRequestAudioDto(
    model = AI_MODEL_NAME_FOR_AUDIO,
    messages = listOf(
        ChatMessageForAudioDto(
            role = USER_ROLE_NAME,
            content = listOf(
                ContentPartDto(
                    type = TEXT_TYPE,
                    text = "Please transcribe this audio to text accurately. НЕ ВОЗВРАЩАЙ НИКАКОГО ТЕКСТА КРОМЕ РАСШИФРОВАННОГО. ЕСЛИ ТЕКСТА НЕТ, ТО ВОЗВРАЩАЙ ПУСТУЮ СТРОКУ"
                ),
                ContentPartDto(
                    type = AUDIO_TYPE,
                    inputAudio = InputAudioDto(
                        data = this,
                        format = "wav"
                    )
                )
            )
        )
    )
)
