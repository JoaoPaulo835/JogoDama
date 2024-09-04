package com.damas.objetos;

public class Dama extends Pedra {

    public Dama(Casa casa, String nomeImage, Cor cor) {
        super(casa, nomeImage, cor);
    }

    /**
     * Movimento da Dama que pode andar várias casas na diagonal
     * @param destino
     * @return boolean. True se puder ser movida e false se não 
     */
    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs((destino.getX() - super.getCasa().getX()));
        int distanciaY = Math.abs((destino.getY() - super.getCasa().getY()));

        if (distanciaX == distanciaY) return true;

        return false;
    }

    @Override
    public boolean podePercorrer(Tabuleiro tabuleiro, int deltaX, int deltaY) {
        int x = super.getCasa().getX();
        int y = super.getCasa().getY();
        int pecasSeguidasNoCaminho = 0;

        while (!((x == -1 || x == 8) || (y == -1 || y == 8))) {
            x += deltaX;
            y += deltaY;
            
            try {
                Pedra pecaAtual = tabuleiro.getCasa(x, y).getPeca();

                if (!( pecaAtual == null)) {
                    pecasSeguidasNoCaminho += 1;
    
                    // verifica se existe peça do mesmo time no caminho, se tiver retorna FALSE;
                    if(pecaAtual.getCor() == this.getCor());
                } else {
                    // Verifica se há peças adversárias no caminho para capturar e permite o movimento
                    if (pecasSeguidasNoCaminho == 1) {
                        return true;
                    }
    
                    if (pecasSeguidasNoCaminho == 2) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }

        // Caso a jogada for totalmente inválida não rodará nenhum movimento
        return false;
    }
    
    @Override
    public boolean podeTransformarParaDama() {return false;}

    @Override
    public void transformarPedraParaDama(){};

    @Override
    public boolean isTipoValido() {
        return super.getCor() == Cor.BRANCA || super.getCor() == Cor.VERMELHA;
    }
}