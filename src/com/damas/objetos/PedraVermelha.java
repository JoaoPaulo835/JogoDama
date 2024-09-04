package com.damas.objetos;

public class PedraVermelha extends Pedra {
    public PedraVermelha(Casa casa) {
        super(casa, "pedra_vermelha", Cor.VERMELHA);
    }

    public boolean podeTransformarParaDama() {
        return super.getCasa().getY() == 0;
    }

    public void transformarPedraParaDama() {
        super.getCasa().colocarPeca(new Dama(super.getCasa(), "dama_vermelha", super.getCor()));
    }

    @Override
    public boolean isTipoValido() {
        return super.getCor() == Cor.VERMELHA;
    }
}