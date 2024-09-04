package com.damas.objetos;

public class PedraBranca extends Pedra {
    public PedraBranca(Casa casa) {
        super(casa, "pedra_branca", Cor.BRANCA);
    }

    public boolean podeTransformarParaDama() {
        return super.getCasa().getY() == 7;
    }

    public void transformarPedraParaDama() {
        super.getCasa().colocarPeca(new Dama(super.getCasa(), "dama_branca", super.getCor()));
    }

    @Override
    public boolean isTipoValido() {
        return super.getCor() == Cor.BRANCA;
    }
}