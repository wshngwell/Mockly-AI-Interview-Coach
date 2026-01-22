
-keep class com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatMessageForAudioDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatRequestAudioDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChatResponseAudioDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForAudio.ChoiceAudioDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForAudio.ContentPartDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForAudio.InputAudioDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.ChatMessageForTextDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.ChatRequestTextDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.ChatResponseTextDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.ChatStreamChunkDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.ChoiceTextDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.StreamChoiceDto { *; }
-keep class com.example.interviewaicoach.data.remote.dto.dtoForText.StreamDeltaDto { *; }


-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type


-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE