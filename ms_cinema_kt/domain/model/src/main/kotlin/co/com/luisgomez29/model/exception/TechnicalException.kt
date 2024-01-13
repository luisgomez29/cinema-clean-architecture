package co.com.luisgomez29.model.exception

import co.com.luisgomez29.model.enums.TechnicalExceptionMessage


class TechnicalException : RuntimeException {
    private val technicalExceptionMessage: TechnicalExceptionMessage

    constructor(technicalExceptionEnum: TechnicalExceptionMessage) : super(technicalExceptionEnum.name) {
        this.technicalExceptionMessage = technicalExceptionEnum
    }

    constructor(message: String?, technicalExceptionMessage: TechnicalExceptionMessage) : super(message) {
        this.technicalExceptionMessage = technicalExceptionMessage
    }

    constructor(cause: Throwable?, message: TechnicalExceptionMessage) : super(cause) {
        this.technicalExceptionMessage = message
    }
}