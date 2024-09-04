package com.damas.objetos;

import java.util.ArrayList;

import com.damas.gui.JanelaFinal;
import com.damas.regras.RegrasDeslocamento;
import com.damas.regras.RegrasJogador;
// import com.damas.regras.RegrasTransformação;

public class Jogo {
    // private RegrasTransformação regrasTransformação;
    private RegrasDeslocamento regrasDeslocamento;
    private RegrasJogador regrasJogador;
    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private int vezAtual = 1; // é necessário iniciar com o valor o valor 1 ou 2
    private int jogadas = 0;
    private int jogadasSemComerPeca = 0;
    private ArrayList<Casa> pecasAComer;
    private Casa casaBloqueadaOrigem;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        pecasAComer = new ArrayList<Casa>();
        jogadorUm = new Jogador("player branco", Cor.BRANCA);
        jogadorDois = new Jogador("player vermelho", Cor.VERMELHA);
        
        vezAtual = 1;
        jogadas = 0;
        jogadasSemComerPeca = 0;
        casaBloqueadaOrigem = null;

        regrasJogador = new RegrasJogador(jogadorUm, jogadorDois);
        regrasDeslocamento = new RegrasDeslocamento(tabuleiro, pecasAComer, regrasJogador);

        colocarPecas(tabuleiro);
    }
    
    /**
     * Realiza uma serie de paços para comandar uma peça na posicão 
     * (origemX, origemY) fazer um movimento para (destinoX, destinoY).
     * 
     * @param origemX - {@code int} linha da Casa de origem.
     * @param origemY - {@code int} coluna da Casa de origem.
     * @param destinoX - {@code int} linha da Casa de destino.
     * @param destinoY - {@code int} coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);

        if(regrasDeslocamento.moverPeca(origem, destino)) {
            realizarMovimento(destino);
        }
    }


    // Verifica se há peças do adversário no caminho e faz o movimento
    // Refatoração aplicada
    private void realizarMovimento(Casa destino) {
        
        if (!(pecasAComer.isEmpty())) {
            comerPecas();
            movimentoComerPecas(destino);
        } else {
            movimentoSemComerPeca();
        }

        jogadas++;
        if(destino.getPeca().podeTransformarParaDama()) {
            destino.getPeca().transformarPedraParaDama();
        }
    }
    
    // Realiza a movimentação comendo peças de outra cor no caminho
    // e verificando se pode continuar comendo mais peças
    // Refatorado
    private void movimentoComerPecas(Casa destino) {
        if (regrasDeslocamento.deveContinuarJogando(destino)) {
            casaBloqueadaOrigem = destino;
        } else {
            regrasJogador.trocarJogadorVez();
        }
    }

    // Realiza a movimentação sem comer peças no caminho
    // Refatorado
    private void movimentoSemComerPeca() {
        jogadasSemComerPeca++;
        regrasJogador.trocarJogadorVez();
    }

    /**
     * Limpa as peças na variável {@code ArrayList pecasAComer}, adiciona pontos ao jogador
     */
    private void comerPecas() {
        int pecasComidas = pecasAComer.size();

        regrasJogador.addPontoJogadorVez(pecasComidas);

        for (Casa casa : pecasAComer) {
            casa.removerPeca();
        }

        pecasAComer.removeAll(pecasAComer);

        jogadasSemComerPeca = 0;
    }

    /**
     * Posiciona peças no tabuleiro.
     * Utilizado na inicialização do jogo.
     * @param tabuleiro - tipo {@code Tabuleiro} onde as peças serão posicionadas
     */
    public void colocarPecas(Tabuleiro tabuleiro) {

        // CRIA E PÕE AS PEÇAS NA PARTE INFERIOR DO TABULEIRO
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = tabuleiro.getCasa(x, y);
                    // Refatorado
                    new PedraBranca(casa);
                    // new PedraBranca(casa, Pedra.PEDRA_BRANCA);
                }
                
                else if ((x % 2 != 0) && (y % 2 != 0)){
                    Casa casa = tabuleiro.getCasa(x, y);
                    // Refatorado
                    new PedraBranca(casa);
                    // new Pedra(casa, Peca.PEDRA_BRANCA);
                }
            }

        }
        // CRIA E POE AS PEÇAS NA PARTE SUPERIOR DO TABULEIRO
        for (int x = 0; x < 8; x++) {
            for (int y = 5; y < 8; y++) {
                if ((x % 2 != 0) && (y % 2 != 0)) {
                    Casa casa = tabuleiro.getCasa(x, y);
                    // Refatorado
                    new PedraVermelha(casa);
                }
                else if ((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = tabuleiro.getCasa(x, y);
                    // Refatorado
                    new PedraVermelha(casa);
                }
            }
        }
    }

    /**
     * 
     * @return
     * {@code int } 0 - Nenhum jogador
     * <li> {@code int} 1 - Jogador um </li>
     * <li> {@code int} 1 - Jogador dois </li>
     */
    public int getGanhador() {
        if (jogadorUm.getPontos() >= 12) return 1;
        if (jogadorDois.getPontos() >= 12) return 2;
        return 0;
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    
    public void setJogadorUm(Jogador jogador) {
        jogadorUm = jogador;
    }

    public void setJogadorDois(Jogador jogador) {
        jogadorDois = jogador;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    /**
     * @return
     *      {@code int} 1 - jogador branco
     *  <li>{@code int} 2 - jogador vermelho </li>
     */
    public int getVez() {
        return vezAtual;
    }

    public int getJogadasSemComerPecas() {
        return jogadasSemComerPeca;
    }

    public int getJogada() {
        return jogadas;
    }

    public Casa getCasaBloqueada() {
        return casaBloqueadaOrigem;
    }

    @Override
    public String toString() {
        JanelaFinal janelaFinal = new JanelaFinal();
        return janelaFinal.janelaFinalizarJogo(regrasJogador, jogadas, jogadasSemComerPeca, casaBloqueadaOrigem);
    }
}