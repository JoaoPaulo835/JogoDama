package com.damas.objetos;

public interface Peca {
    
    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    /**
     * Movimenta a peca para uma nova casa. Antes de trocar de casa a Peca armazena a casa no
     * campo ultimaCasa, isso servirá para verificar qual sentido em que a peça se moveu para
     * implementar a lógica de comer peças.
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino);
    
    /**
     * Implementa a regra de movimento da peça
     * @param destino - tipo {@code Casa} destino da peça
     * @return {@code boolean}
     */
    public boolean isMovimentoValido(Casa destino);

    // Refatorado para criar uma fluidez melhor na cor
    public Cor getCor();

    // Refatorado
    public String getNomeImagem();

    public boolean podeMover(int distanciaX, int distanciaY, int sentidoY);

    // Refatorado
    public boolean podePercorrer(Tabuleiro tabuleiro, int deltaX, int deltaY);

    public boolean podeTransformarParaDama();

    public void transformarPedraParaDama();

    public boolean isTipoValido();
}