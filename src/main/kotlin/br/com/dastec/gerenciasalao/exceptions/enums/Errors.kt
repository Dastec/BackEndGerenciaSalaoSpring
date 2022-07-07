package br.com.dastec.gerenciasalao.exceptions.enums

enum class Errors(val message: String, val internalCode: String) {
    GSL000("Unathorized", "GSL-000"),
    GSL001("Usuário %s não encontrado!", "GSL-001"),
    GSL002("Erro ao logar!", "GSL-002"),
    GSL003("Token inválido!", "GSL-003"),

    GS001("Invalid Request", "GS-001" ),

    GS101("Cliente nº%s não foi encontrado!", "GS-101"),
    GS102("Cliente %s já tem um atendimento em aberto!", "GS-102"),
    GS103("Cliente %s já tem um atendimento criado ou aberto!", "GS-103"),

    GS201("Categoria nº%s não foi encontrada!", "GS-201"),

    GS301("Serviço nº%s não foi encontrado!", "GS-301"),
    GS302("Nenhum serviço foi encontrado!", "GS-302"),

    GS401("Forma de pagamento nº%s não foi encontrada!", "GS-401"),
    GS402("Valor do pagamento não deve ultrapassar o valor das pendências selecionadas!", "GS-402"),
    GS403("Valor do pagamento não é suficiente para pagar as pendêcias selecionadas!", "GS-403"),

    GS501("Atendimento nº%s não foi encontrado!", "GS-501"),
    GS502("Atendimento nº%s sem pendência!", "GS-502"),
    GS503("Atendimento nº%s não está com status aberto!", "GS-503"),
    GS504("Atendimento nº%s não está com pendência!", "GS-504"),
    GS505("Atendimento nº%s já está cancelado!", "GS-505"),
    GS506("Atendimento nº%s já está finalizado!", "GS-506"),
    GS507("Não é possível adicionar duas vezes o mesmo serviço dentro de um atendimento!", "GS-507"),
    GS508("O atendimento não tem nenhum serviço adicionado!", "GS-508"),
    GS509("Nenhum atendimento encontrado!", "GS-509"),

    GS601("Pedência nº%s não foi encontrada!", "GS-601"),

    GS701("Pagamento nº%s não foi encontrado!", "GS-701"),
    GS702("Pagamento ultrapassa o valor do atendimento %s!", "GS-702"),
    GS703("Pagamento ultrapassa o valor dos atendimentos selecionados!", "GS-703"),

    GS1001("A senhas e a confimarção devem ser iguais!", "GS-1001")
}