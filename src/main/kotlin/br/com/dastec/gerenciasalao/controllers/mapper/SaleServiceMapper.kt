package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.extensions.toServiceResponse
import br.com.dastec.gerenciasalao.controllers.responses.SaleServiceResponse
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.services.SaleServiceModelService
import org.springframework.stereotype.Component

@Component
class SaleServiceMapper(private val saleServiceModelService: SaleServiceModelService) {

    fun toSaleServiceResponse(saleServices: MutableList<SaleServiceModel>): MutableList<SaleServiceResponse> {
        val saleServiceResponses: MutableList<SaleServiceResponse> = mutableListOf()
        for (seleService in saleServices) {
            val saleService = SaleServiceResponse(
                idSaleService = seleService.idSaleService!!,
                service = seleService.service.toServiceResponse(),
                price = seleService.price
            )
            saleServiceResponses.add(saleService)
        }
        return saleServiceResponses
    }
}