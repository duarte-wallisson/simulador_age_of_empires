/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.enumerador;

/**
 *
 * @author wallisson
 */
public enum SituacaoJogador {
    NADA("nada"),
    CRIADOR("criador"),
    CONVIDADO("convidado"),
    AGUARDANDO("aguardando"),
    JOGANDO("jogando");
    
    private String literal;

    private SituacaoJogador(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
    
}
