package com.damas.regras;

import com.damas.objetos.Jogador;

public class RegrasJogador {
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private Jogador jogadorVez;

    public RegrasJogador(Jogador jogadorUm, Jogador jogadorDois) {
        this.jogadorUm = jogadorUm;
        this.jogadorDois = jogadorDois;
        this.jogadorVez = jogadorUm;
    }

    public Jogador getJogadorVez() {
        return jogadorVez;
    }

    public void trocarJogadorVez() {
        jogadorVez = (jogadorVez == jogadorUm) ? jogadorDois : jogadorUm;
    }

    // Diminuindo o acoplamento
    public void addPontoJogadorVez(int pontos) {
        jogadorVez.addPonto(pontos);
    }

    public String getNomeJogadorVez() {
        return jogadorVez.getNome();
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }
}