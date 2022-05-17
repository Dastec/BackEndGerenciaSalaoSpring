package br.com.dastec.gerenciasalao.exceptions.enums

enum class Errors(val message: String, val internalCode: String) {

    GS001("Invalid Request", "GS-001" ),

    GS101("Cliente nº%s não foi encontrado!", "GS-101"),
    GS102("Cliente %s já tem um atendimento em aberto!", "GS-102"),

    GS201("Categoria nº%s não foi encontrada!", "GS-201"),

    GS301("Serviço nº%s não foi encontrado!", "GS-301"),
    GS302("Nenhum serviço foi encontrado!", "GS-302"),

    GS401("Forma de pagamento nº%s não foi encontrada!", "GS-401"),

    GS501("Atendimento nº%s não foi encontrado!", "GS-501"),
    GS502("Atendimento nº%s sem pendência!", "GS-502"),
    GS503("Atendimento nº%s não está com status aberto!", "GS-503"),
    GS504("Atendimento nº%s não está com pendência!", "GS-504"),

    GS601("Pedência nº%s não foi encontrada!", "GS-601"),

    GS701("Pagamento nº%s não foi encontrado!", "GS-701"),
    GS702("Pagamento ultrapassa o valor total do atendimento %s!", "GS-702"),
    GS703("Pagamento ultrapassa o valor total dos atendimentos selecionados com pendências!", "GS-703")
}