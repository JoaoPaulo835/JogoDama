package com.damas.objetos;

public abstract class Pedra implements Peca {
    private Casa casa;
    private String nomeImagem;
    private Cor cor;

    public Pedra(Casa casa, String nomeImagem, Cor cor) {
        this.casa = casa;
        this.nomeImagem = nomeImagem;
        this.cor = cor;
        casa.colocarPeca(this);
    }
    
    public Casa getCasa() {
        return casa;
    }

    public boolean podeMover(int distanciaX, int distanciaY, int sentidoY) {
        return (distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == cor.getSentido();
    }

    @Override
    public String getNomeImagem() {
        return nomeImagem;
    }
    
    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {

        // SENTIDO UNITÁRIO E DISTANCIA X E Y DA CASA ATUAL ATÉ A CASA DE DESTINO
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        // REGRA DE MOVIMENTO NO CASO DA DISTÂNCIA SER DE 2 CASAS (MOVIMENTO DE COMER PEÇA)
        if ((distanciaX <= 2 || distanciaY <= 2) && (distanciaX == distanciaY)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean podePercorrer(Tabuleiro tabuleiro, int deltaX, int deltaY) {
        int x = casa.getX();
        int y = casa.getY();
        x += deltaX;
        y += deltaY;

        try {
            Pedra pecaAtual = tabuleiro.getCasa(x, y).getPeca();
            
            if (!( pecaAtual == null)) {
                if (tabuleiro.getCasa((x + deltaX), (y + deltaY)).getPeca() != null) {
                    return false;
                }

                // VERIFICA SE A PEÇA NO CAMINHO É DA MESMA COR
                if(pecaAtual.getCor() == this.cor){
                    return false;
                }
                return true;
            }

        } catch (Exception e) {
            return false;
        }

        // Verificação para caso não capture nenhuma peça e se torne inválido
        // Refatorado
        return false;
    }
}