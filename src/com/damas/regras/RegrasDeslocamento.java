package com.damas.regras;

import java.util.ArrayList;

import com.damas.objetos.Casa;
import com.damas.objetos.Peca;
import com.damas.objetos.Tabuleiro;

public class RegrasDeslocamento {
    private Tabuleiro tabuleiro;
    private ArrayList<Casa> pecasAComer;
    private RegrasJogador regrasJogador;

    public RegrasDeslocamento(Tabuleiro tabuleiro, ArrayList<Casa> pecasAComer, RegrasJogador regrasJogador) {
        this.tabuleiro = tabuleiro;
        this.pecasAComer = pecasAComer;
        this.regrasJogador = regrasJogador;
    }

    public boolean moverPeca(Casa origem, Casa destino) {
        Peca peca = origem.getPeca();

        if(regrasJogador.getJogadorVez().getCor() != origem.getPeca().getCor()) return false;

        if (peca != null && peca.isMovimentoValido(destino)) {
            if (simularMovimentoEValidar(origem, destino)) {
                peca.mover(destino);
                return true;
            }
        }
        return false;
    }

        /**
     * <p>
     * Percorre as casas da casa de origem clicada até a casa de destino clicada,
     * verifica se o caminho é valido e adiciona casas a variável {@code pecasAComer} 
     * </p> 
     * 
     * @param origem {@code Casa} de origem
     * @param destino {@code Casa} de destino
     * @return {@code boolean} se a simulação ocorreu bem
     */
    private boolean simularMovimentoEValidar(Casa origem, Casa destino) {
        Peca peca = origem.getPeca();
        int casasComPecaSeguidas = 0;

        // Se tiver peça não pode se movimentar
        if (destino.getPeca() != null) return false;

        // SENTIDO DO MOVIMENTO E DISTÂNCIA DO MOVIMENTO
        int sentidoX = (destino.getX() - origem.getX());
        int sentidoY = (destino.getY() - origem.getY());
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        // Se não sai do lugar
        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        sentidoX = sentidoX/distanciaX;
        sentidoY = sentidoY/distanciaY;

        // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 2 BLOCOS
        if ((distanciaX == 2 && distanciaY == 2) && (peca.isTipoValido())) {
            Casa casa = tabuleiro.getCasa((destino.getX() - sentidoX), (destino.getY() - sentidoY));
            if (casa.getPeca() == null) return false;
        } else {
            // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 1 BLOCO
            //return peca.getCor().podeMover(distanciaX, distanciaY, sentidoY);
            return peca.podeMover(distanciaX, distanciaY, sentidoY);
        }

        //PERCORRER AS CASAS E VERIFICAR:
        // 1 - SE HÁ MAIS DE UMA PEÇA SEGUIDA NO CAMINHO (VERDADEIRO RETORNA FALSO)
        // 2 - SE HÁ UMA PEÇA NO CAMINHO E É DA MESMA COR (VERDADEIRO RETORNA FALSO)
        int i = origem.getX();
        int j = origem.getY();

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = tabuleiro.getCasa(i, j);
            Peca pecaAlvo = alvo.getPeca();

            if (pecaAlvo != null) {
                casasComPecaSeguidas += 1;

                if(peca.getCor() == pecaAlvo.getCor()) {
                    if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                    return false;
                }
            } else {

                // VERIFICA SE HÁ PEÇA PARA 'COMER' NO CAMINHO E PASSAR A CASA À COLEÇÃO pecasAComer() PARA DEPOIS COMÊ-LAS
                if (casasComPecaSeguidas == 1) {
                    Casa casa = tabuleiro.getCasa((alvo.getX() - sentidoX), (alvo.getY() - sentidoY));
                    pecasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Dispara o método {@code percorrerEVerificar()} no sentido
     * das quatro diagonais a partir da casa indicada.
     * </p>
     * @param origem tipo {@code Casa} de onde vai partir a verifição
     * @return {@code true} Se há peça para comer em alguma diagonal
     */
    public boolean deveContinuarJogando(Casa origem) {
        if (percorrerEVerificar(origem, Tabuleiro.X_ESQUERDA, Tabuleiro.Y_CIMA, tabuleiro)) {
            return true;
        } else {
            
            if (percorrerEVerificar(origem, Tabuleiro.X_DIREITA, Tabuleiro.Y_CIMA, tabuleiro)) {
                return true;
            } else {
                        
                if (percorrerEVerificar(origem, Tabuleiro.X_DIREITA, Tabuleiro.Y_BAIXO, tabuleiro)) {
                    return true;
                } else {

                    if (percorrerEVerificar(origem, Tabuleiro.X_ESQUERDA, Tabuleiro.Y_BAIXO, tabuleiro)) {
                        return true;
                    }                    
                }
            }

        }

        return false;
    }

        /**
     * <p>
     * Percorre as casas do tabuleirio a partir da casa de origem indicada no sentido dado
     * por {@code sentidoX} e {@code sentidoY} até o limite do tabuleiro.
     * </p>
     * @param origem Casa de origem da peça
     * @param deltaX {@code Tabuleiro.X_ESQUERDA} ou {@code Tabuleiro.X_DIREITA} 
     * @param deltaY {@code Tabuleiro.Y_BAIXO} ou {@code Tabuleiro.Y_CIMA}
     * @return
     * {@code false} - se não há peça para comer
     * <li> {@code false} - se houver mais de uma peça no caminho </li>
     * <li> {@code false} - se houver peça de mesma cor no caminho </li>
     * <li> {@code true} - se há peça para comer </li>
     */
    public boolean percorrerEVerificar(Casa origem, int deltaX, int deltaY, Tabuleiro tabuleiro) {
        return origem.getPeca().podePercorrer(tabuleiro, deltaX, deltaY);
    }
}