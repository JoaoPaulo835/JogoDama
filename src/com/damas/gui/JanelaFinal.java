package com.damas.gui;

import com.damas.objetos.Casa;
import com.damas.regras.RegrasJogador;

public class JanelaFinal {
    public String janelaFinalizarJogo(RegrasJogador regrasJogador, int jogadas, int jogadasSemComerPeca, Casa casaBloqueadaOrigem) {
        String retorno = "Vez: ";

        retorno += regrasJogador.getNomeJogadorVez();
        retorno += "\n";

        retorno += "Nº de jogadas: " + jogadas + "\n";
        retorno += "Jogadas sem comer peça: " + jogadasSemComerPeca + "\n";
        retorno += "\n";
        retorno += "Informações do(a) jogador(a) " + regrasJogador.getJogadorUm().getNome() + "\n";
        retorno += "Pontos: " + regrasJogador.getJogadorUm().getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - regrasJogador.getJogadorDois().getPontos()) + "\n";
        retorno += "\n";        
        retorno += "Informações do(a) jogador(a) " + regrasJogador.getJogadorDois().getNome() + "\n";
        retorno += "Pontos: " + regrasJogador.getJogadorDois().getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - regrasJogador.getJogadorUm().getPontos()) + "\n";

        if (casaBloqueadaOrigem != null) {
            retorno += "\n";
            retorno += "Mova a peça na casa " + casaBloqueadaOrigem.getX() + ":" + casaBloqueadaOrigem.getY() + "!";
        }

        return retorno;
    }
}