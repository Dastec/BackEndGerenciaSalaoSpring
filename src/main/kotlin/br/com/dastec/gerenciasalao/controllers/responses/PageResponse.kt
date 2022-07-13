package br.com.dastec.gerenciasalao.controllers.responses

class PageResponse<T>(
    var items: List<T>,
    var currentPage: Int,
    var totalItems: Long,
    var totalPages: Int,
)