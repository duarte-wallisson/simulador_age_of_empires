/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.enums;

import Entidades.Prefeitura;

/**
 *
 * @author wallisson
 */
public enum Poder {
    NUVEM_GAFANHOTOS(1, "Destruir metade das fazendas do inimigo selecionado", false, 1000, 50*Prefeitura.getHora()),
    MORTE_PRIMOGENITOS(2, "Matar metade dos aldeões do inimigo selecionado", false, 1500, 100*Prefeitura.getHora()),
    CHUVA_PEDRAS(3, "Destruir metade das fazendas, metade das minas de ouro e metade da maravilha Também matar metade dos aldeões do inimigo selecionado", false, 2000, 200*Prefeitura.getHora()),
    PROTECAO_NUVEM_GAFANHOTOS(4, "Proteção total contra o lançamento de nuvem de gafanhoto", false, 5000, 500*Prefeitura.getHora()),
    PROTECAO_MORTE_PRIMOGENITOS(5, "proteção total contra o lançamento de morte dos primogênitos", false, 6000, 600*Prefeitura.getHora()),
    PROTECAO_CHUVA_PEDRAS(6, "proteção total contra o lançamento de chuva de pedras", false, 7000, 700*Prefeitura.getHora());
    
    private int codigo;
    private String funcao;
    private boolean habilitado;
    private int custo;
    private int tempo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    
    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    private Poder(int codigo, String funcao, boolean habilitado, int custo, int tempo) {
        this.codigo = codigo;
        this.funcao = funcao;
        this.habilitado = habilitado;
        this.custo = custo;
        this.tempo = tempo;
    }
    
}
