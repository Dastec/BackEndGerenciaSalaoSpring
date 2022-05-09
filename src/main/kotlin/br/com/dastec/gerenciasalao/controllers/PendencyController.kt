package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.PendencyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/pendency")
class PendencyController(
    private val pendencyService: PendencyService,
    private val customerServiceModelService: CustomerServiceModelService
    ) {

}