fun validaTabuleiro(numLinhas : Int,numColunas:Int) : Boolean {
    return (numLinhas in 5..7) && (numColunas == numLinhas + 1)
}
fun processaColuna(numColunas : Int, coluna : String?) : Int? {
    if (coluna != null){
        if (coluna.length == 1 && coluna[0] in 'A'..'Z') {
            val verificanum = coluna[0] - 'A'
            if (verificanum < numColunas) {
                return verificanum
            }
        }
    }
    return null
}
fun nomeValido(nome: String) : Boolean {
    if (nome.length in 3..12) {
        var verificar = 0
        while (verificar < nome.length) {
            if (nome[verificar] == ' ') {
                return false
            }
            verificar++
        }
        return true
    }
    return false
}
fun criaTopoTabuleiro(numColunas: Int) :String {
    val topoLado = "\u2554"
    var fim4 = 0
    var meio = "\u2550"
    while ((numColunas * 4)-2 > fim4){
        meio = meio + "\u2550"
        fim4++
    }
    val topoFinal = "\u2557"
    return topoLado + meio + topoFinal
}
fun criaLegendaHorizontal(numColunas: Int): String {
    if (numColunas !in 1..26) {
        return "Numero de colunas invalido"
    }
    var legenda = " " // começa com 1 espaço em branco
    var contador = 0 // variável auxiliar
    while(contador < numColunas){ // percorrer as colunas
        val letraInicial = 'A' + contador // ele vai percorrer as letras do alfabeto porque os chars têm valores por trás
        legenda += " " + letraInicial + " " // sintaxe
        if(contador+1 != numColunas){ // para não meter o último |
            legenda += "|"
        }
        contador++
    }
    legenda += " "
    return legenda
}
fun criaTabuleiro(tabuleiro: Array<Array<String?>>,mostraLegenda : Boolean = true) : String{
    val numLinhas = tabuleiro.size
    val numColunas = tabuleiro[0].size
    val topototal = criaTopoTabuleiro(numColunas) + "\n"
    val lateral = "\u2551"
    var tabuleirovisual = topototal
    for (i in 0..numLinhas -1){
        var linha = lateral
        for (j in 0..numColunas - 1 ){
            var caracter =  if (tabuleiro[i][j] == null) " " else tabuleiro[i][j]
            linha += " $caracter "
            linha+= if (j < numColunas -1 ) "|" else lateral
        }
        linha += if (i < numLinhas-1) "\n" else ""
        tabuleirovisual+= linha
    }

    if (mostraLegenda) {
        tabuleirovisual += "\n" + criaLegendaHorizontal(numColunas)
    }
    return tabuleirovisual
}
fun criaTabuleiroVazio(numLinhas : Int, numColunas: Int) :Array<Array<String?>> {
    return Array(numLinhas) { arrayOfNulls(numColunas) }
}
fun contaBaloesLinha(tabuleiro: Array<Array<String?>>, linha: Int) : Int{
    var quantidadebaloeslinha = 0
    for (coluna in tabuleiro[linha]){
        if (coluna == "\u001b[31m\u03D9\u001b[0m" || coluna == "\u001b[34m\u03D9\u001b[0m"){
            quantidadebaloeslinha++
        }
    }
    return quantidadebaloeslinha
}
fun contaBaloesColuna(tabuleiro: Array<Array<String?>>, coluna: Int) : Int{
    var quantidadebaloescoluna = 0
    for (linha in tabuleiro.indices){
        if (tabuleiro[linha][coluna] == "\u001B[31m\u03D9\u001b[0m" || tabuleiro[linha][coluna] == "\u001B[34m\u03D9\u001b[0m" ){
            quantidadebaloescoluna++
        }
    }
    return quantidadebaloescoluna
}
fun colocaBalao(tabuleiro: Array<Array<String?>>, coluna: Int, humano: Boolean) : Boolean{
    val baloesNaColuna = contaBaloesColuna(tabuleiro, coluna)
    if (baloesNaColuna <= tabuleiro[0].size) {
        var linha = 0
        while (linha < tabuleiro.size) {
            if (tabuleiro[linha][coluna] == null) {
                tabuleiro[linha][coluna] = if (humano) "\u001b[31m\u03D9\u001b[0m" else "\u001B[34m\u03D9\u001b[0m"
                return true
            }
            linha++
        }
    }
    return false
}
fun jogadaNormalComputador(tabuleiro: Array<Array<String?>>): Int?{
    for (linha in 0..tabuleiro.size - 1) {
        for (coluna in 0..tabuleiro[0].size -1){
            if (tabuleiro[linha][coluna]== null){
                return coluna
            }
        }
    }
    return null
}
fun eVitoriaHorizontal(tabuleiro: Array<Array<String?>>) : Boolean{
    for (linha in 0..tabuleiro.size - 1) {
        var contadorvermelho = 0
        var contadorazul = 0
        for (coluna in 0..tabuleiro[0].size - 1) {
            if (tabuleiro[linha][coluna] == "\u001b[31m\u03D9\u001b[0m") {
                contadorvermelho++
                if (contadorvermelho == 4) return true
            } else {
                contadorvermelho = 0
            }
            if (tabuleiro[linha][coluna] == "\u001b[34m\u03D9\u001b[0m") {
                contadorazul++
                if (contadorazul == 4) return true
            } else {
                contadorazul = 0
            }
        }
    }
    return false
}
fun eVitoriaVertical(tabuleiro: Array<Array<String?>>) : Boolean{
    for (coluna in 0..tabuleiro[0].size - 1) {
        var contadorvermelho = 0
        var contadorazul = 0
        for (linha in 0..tabuleiro.size - 1) {
            if (tabuleiro[linha][coluna] == "\u001b[31m\u03D9\u001b[0m") {
                contadorvermelho++
                if (contadorvermelho == 4) return true
            } else {
                contadorvermelho = 0
            }
            if (tabuleiro[linha][coluna] == "\u001b[34m\u03D9\u001b[0m") {
                contadorazul++
                if (contadorazul == 4) return true
            } else {
                contadorazul = 0
            }
        }
    }
    return false
}
fun eVitoriaDiagonal(tabuleiro: Array<Array<String?>>) : Boolean{

    for (linha in 0..tabuleiro.size - 4) {
        for (coluna in 0..tabuleiro[0].size - 4) {
            for (caracter in arrayOf("\u001B[31m\u001B[0m","\u001B[34m\u001B[0m")){
                if (tabuleiro[linha][coluna] == caracter &&
                    tabuleiro[linha + 1][coluna + 1] == caracter &&
                    tabuleiro[linha + 2][coluna + 2] == caracter &&
                    tabuleiro[linha + 3][coluna + 3] == caracter) {
                    return true
                }
            }
        }
    }
    return false
}
fun ganhouJogo(tabuleiro: Array<Array<String?>>) : Boolean{
    return eVitoriaHorizontal(tabuleiro) || eVitoriaVertical(tabuleiro) || eVitoriaDiagonal(tabuleiro)
}
fun eEmpate(tabuleiro: Array<Array<String?>>) : Boolean {
    for (linha in tabuleiro) {
        for (coluna in linha) {
            if (coluna == null) {
                return false
            }
        }
    }
    return true
}
fun explodeBalao(tabuleiro: Array<Array<String?>>, coordenadas: Pair<Int,Int> ) : Boolean{
    return true
}
fun jogadaExplodirComputador(tabuleiro: Array<Array<String?>>) : Pair<Int,Int> {
    return Pair(1,2)
}
fun leJogo(nomeFicheiro: String): Pair<String,Array<Array<String?>>> {
    return Pair("",Array(2) { arrayOfNulls(2) })
}
fun gravaJogo(nomeFicheiro: String, tabuleiro: Array<Array<String?>>, nomeJogador: String) {

}
fun main() {
    println()
    println("Bem-vindo ao jogo \"4 Baloes em Linha\"!")
    do {
        println()
        println("1. Novo Jogo\n2. Gravar Jogo\n3. Ler Jogo\n0. Sair")
        println()
        val escolha = readln().toIntOrNull()
        when (escolha) {
            1 -> {
                var linhas: Int?
                var colunas: Int?
                do {
                    do {
                        println("Numero de linhas:")
                        linhas = readln().toIntOrNull()
                        if (linhas == null || linhas !in 5..7) {
                            println("Numero invalido")
                        }
                    } while (linhas == null || linhas !in 5..7)
                    do {
                        println("Numero de colunas:")
                        colunas = readln().toIntOrNull()
                        if (colunas == null || colunas < 0) {
                            println("Numero invalido")
                        }
                    } while (colunas == null || colunas < 0)
                    var fim2 = 0
                    if (validaTabuleiro(linhas, colunas)) {
                        var nome: String
                        do {
                            println("Nome do jogador 1:")
                            nome = readln()
                            if (nomeValido(nome)) {
                                val tabuleiro: Array<Array<String?>> = criaTabuleiroVazio(linhas, colunas)
                                val tabuleiroVisual = criaTabuleiro(tabuleiro)
                                println(tabuleiroVisual)
                                do {
                                    var fim10 = 0
                                    val letradacoluna = ('A' + colunas - 1)
                                    println()
                                    println("$nome: \u001b[31m\u03D9\u001b[0m")
                                    println("Tabuleiro ${linhas}X${colunas}")
                                    println("Coluna? (A..$letradacoluna):")
                                    val letra = readln()
                                    var fim = 0
                                    val colunaEscolhida = processaColuna(colunas, letra)
                                    var vitoriapessoa = false
                                    if (colunaEscolhida != null) {
                                        println("Coluna escolhida: $letra")
                                        var balao = colocaBalao(tabuleiro,colunaEscolhida,true)
                                        println(criaTabuleiro(tabuleiro))
                                        println()
                                        vitoriapessoa = ganhouJogo(tabuleiro)
                                        if (vitoriapessoa == true){
                                            println("Parabens $nome! Ganhou!")
                                            fim10++
                                        }
                                    } else {
                                        println("Coluna invalida")
                                    }
                                    var jogadadoPc= jogadaNormalComputador(tabuleiro)
                                    if (vitoriapessoa == false && jogadadoPc != null ){
                                        var jogadadoPcstr = ('A' + jogadadoPc)
                                        var pc = colocaBalao(tabuleiro,jogadadoPc,false)
                                        println("Computador: \u001b[34m\u03D9\u001b[0m")
                                        println("Tabuleiro ${linhas}X${colunas}")
                                        println("Coluna escolhida: $jogadadoPcstr")
                                        println(criaTabuleiro(tabuleiro))
                                        var vitoriaPC = ganhouJogo(tabuleiro)
                                        if (vitoriaPC==true){
                                            println()
                                            println("Perdeu! Ganhou o Computador.")
                                            fim10++
                                        }
                                    }
                                } while (fim10 != 1 || eEmpate(tabuleiro))

                            } else {
                                println("Nome de jogador invalido")
                            }
                        } while (!nomeValido(nome))
                        fim2++
                    } else {
                        println("Tamanho do tabuleiro invalido")
                    }
                } while (fim2 != 1)
            }
            2 -> println("POR IMPLEMENTAR") // Gravar Jogo
            3 -> println("POR IMPLEMENTAR") // Ler Jogo
            0 -> println("A sair...")
            else -> println("Opcao invalida. Por favor, tente novamente.")
        }
  } while (escolha == 1)
}