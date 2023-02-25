/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wallisson
 */
public class Maravilha {
    private final int codigo = 1;
    private String funcao = "Ganhar o jogo";
    private int tijolos = 0;
    private int fim = 100000;
    private List<Aldeao> Construtores = new ArrayList<>();

    public Maravilha() {
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getTijolos() {
        return tijolos;
    }

    public void setTijolos(int tijolos) {
        this.tijolos = tijolos;
    }

    public List<Aldeao> getConstrutores() {
        return Construtores;
    }

    public void setConstrutores(List<Aldeao> Construtores) {
        this.Construtores = Construtores;
    }

    public int getFim() {
        return fim;
    }

    public void setFim(int fim) {
        this.fim = fim;
    }   
}
