package com.movies.base
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.Transient


@Serializable
sealed class BaseResponse(
    @Transient
    open val statusCode: HttpStatusCode = HttpStatusCode.OK
){


    @Serializable
    @SerialName("success_response")
    data class SuccessResponse(
        val data: SerializableDTO?,
        val message: String? = null,
        @Transient
        override val statusCode: HttpStatusCode  = HttpStatusCode.OK
    ) : BaseResponse(statusCode = statusCode)

    @Serializable
    @SerialName("error_response")
    data class ErrorResponse(
        val message: String,
        @Transient
        override val statusCode: HttpStatusCode  = HttpStatusCode.BadRequest
    ) : BaseResponse(statusCode)
}
interface SerializableDTO


