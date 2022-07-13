package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.responses.PageResponse
import org.springframework.data.domain.Page

fun<T> Page<T>.toPageResponse(): PageResponse<T>{
    return PageResponse(
        this.content,
        this.number,
        this.totalElements,
        this.totalPages
    )
}