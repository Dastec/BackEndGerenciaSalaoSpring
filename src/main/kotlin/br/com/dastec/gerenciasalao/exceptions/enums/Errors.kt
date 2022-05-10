package br.com.dastec.gerenciasalao.exceptions.enums

enum class Errors(val message: String, val internalCode: String) {

    GS001("Invalid Request", "GS-001" ),
    GS101("Cliente [%s] não foi encontrado!", "GS-101"),
    GS201("Categoria [%s] não foi encontrada!", "GS-201"),
    GS301("Serviço [%s] não foi encontrado!", "GS-301"),
    GS401("Forma de pagamento [%s] não foi encontrada!", "GS-401"),
    GS501("Atendimento [%s] não foi encontrado!", "GS-501"),
    GS601("Pedência [%s] não foi encontrada!", "GS-601")
}