class Simulacao (var id :Int, var valor: Double, var ponto: Double, var bonificacao: Double, var milheiro: Double) {
    var retorno: Double = valor * ponto
    var valorFinal: Double? = null
    fun simular(): Double {
        retorno += (bonificacao / 100) * retorno
        retorno = (retorno/1000) * milheiro
        return valor - retorno
    }
}