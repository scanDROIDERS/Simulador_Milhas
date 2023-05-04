import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.math.abs

fun main() {
    val file = File("simulacoes.csv")

    while (true) {
        println("Selecione a operação desejada:")
        println("1 - Criar tabela")
        println("2 - Fazer simulação")
        println("3 - Visualizar simulações")
        println("4 - Editar tabela")
        println("5 - Deletar simulação")
        println("6 - Deletar tabela")
        println("7 - Sair")

        val opcao = readLine()?.toIntOrNull() ?: continue //verifica se o usuário escreveu alguma coisa, se não tiver escrito o menu é chamado novamente

        when (opcao) {
            1 -> criarTabela(file)
            2 -> adicionarLinha(file)
            3 -> lerTabela(file)
            4 -> editarTabela(file)
            5 -> deletarLinha(file)
            6 -> deletarTabela(file)
            7 -> return
            else -> println("Opção inválida")
        }
    }
}

fun criarTabela(file: File) {
    val writer = FileWriter(file)

    writer.write("Index, Valor, Ponto por real, Bonificacao, Milheiro, Retorno, Valor real gasto\n")
    writer.close()

    println("Tabela criada com sucesso")
}

fun editarTabela(file: File) {
    val reader = FileReader(file)

    val lines = reader.readLines().toMutableList()
    reader.close()
    println("Digite o id da simulação que deseja editar:")
    val idet = readLine() ?: return


    var index = -1
    for ((i, line) in lines.withIndex()) {
        val fields = line.split(",")
        if (fields[0] == idet) {
            index = i
            break
        }
    }

    if (index == -1) {
        println("Simulação não encontrado")
        return
    }

    println("Digite o valor da simulação:")
    val valor = readln().toDoubleOrNull() ?: return


    val fields = lines[index].split(",").toMutableList()

    val simuet = Simulacao(fields[0].toInt(), valor, fields[2].toDouble(), fields[3].toDouble(), fields[4].toDouble())
    val real = simuet.simular()
    val novaLinha = "${fields[0].toInt()},${valor},${fields[2].toDouble()},${fields[3].toDouble()},${fields[4].toDouble()},${abs(real - valor)},$real"

    lines[index] = novaLinha

    val writer = FileWriter(file)

    writer.write(lines.joinToString("\n"))

    writer.close()

    println("Simulação editada com sucesso")
}

fun deletarLinha(file: File) {
    val reader = FileReader(file)

    val lines = reader.readLines().toMutableList()
    reader.close()

    println("Digite o id da simulação que deseja deletar:")
    val idel = readLine() ?: return

    var index = -1
    for ((i, line) in lines.withIndex()) {
        val fields = line.split(",")
        if (fields[0] == idel) {
            index = i
            break
        }
    }

    if (index == -1) {
        println("Simulação não encontrada")
        return
    }

    lines.removeAt(index)

    val writer = FileWriter(file)

    writer.write(lines.joinToString("\n"))

    writer.close()

    println("Simulação deletada com sucesso")
}

fun adicionarLinha(file: File) {
    val reader = FileReader(file)

    val lines = reader.readLines().toMutableList()
    reader.close()

    println("Digite o numero da simulação:")
    val id = readLine()?.toInt() ?: return

    println("Digite o valor do produto:")
    val produto = readLine()?.toDoubleOrNull() ?: return

    println("Digite quantos reais cada ponto vale:")
    val pontoReal = readLine()?.toDoubleOrNull() ?: return

    println("Digite a bonificação:")
    val bonificacao = readLine()?.toDoubleOrNull() ?: return

    println("Digite o valor do milheiro (1000 pontos):")
    val milheiro = readLine()?.toDoubleOrNull() ?: return

    val simu = Simulacao(id, produto, pontoReal, bonificacao, milheiro)
    val real = simu.simular()
    val novaLinha = "$id,$produto,$pontoReal,$bonificacao,$milheiro,${abs(real - produto)},$real"
    lines.add(novaLinha)

    val writer = FileWriter(file)

    writer.write(lines.joinToString("\n"))

    writer.close()

    println("Simulação adicionada com sucesso")
}

fun lerTabela(file: File) {
    val reader = FileReader(file)

    val lines = reader.readLines()
    reader.close()

    if (lines.isEmpty()) {
        println("A tabela está vazia")
        return
    }

    for (line in lines) {
        val fields = line.split(",")
        val id = fields[0]
        val valor = fields[1]
        val pontoReal = fields[2]
        val bonificacao = fields[3]
        val milheiro = fields[4]
        val retorno = fields[5]
        val valorFinal = fields[6]
        println("$id\t| $valor\t| $pontoReal\t| $bonificacao\t| $milheiro\t| $retorno\t| $valorFinal\t|")
    }
}

fun deletarTabela(file: File) {
    if (file.exists()) {
        file.delete()
        println("Tabela deletada com sucesso")
    } else {
        println("Tabela não encontrada")
    }
}