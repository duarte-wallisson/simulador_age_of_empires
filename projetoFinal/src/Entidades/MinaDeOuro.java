/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Entidades.enums.AcaoAldeao;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author wallisson
 */
public class MinaDeOuro {
    private int codigo;
    private final String Funcao = "produzir ouro";
    private List<Aldeao> mineradores = new ArrayList<>();
    private static int capacidade = 5;
    private Semaphore espaco;
    private static int incCodigo = 1;

    public MinaDeOuro(int codigo) {
        this.codigo = codigo;
        this.espaco = new Semaphore(capacidade, true);
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFuncao() {
        return Funcao;
    }

    public List<Aldeao> getMineradores() {
        return mineradores;
    }

    public void setMineradores(Aldeao aldeao) {
        this.mineradores.add(aldeao);
    }

    public static int getCapacidade() {
        return capacidade;
    }

    public static void setCapacidade(int capacidade) {
        MinaDeOuro.capacidade = capacidade;
    }

    public Semaphore getEspaco() {
        return espaco;
    }

    public void setEspaco(Semaphore espaco) {
        this.espaco = espaco;
    }
       
    public static int getIncCodigo() {
        return incCodigo;
    }

    public static void setIncCodigo(int incCodigo) {
        MinaDeOuro.incCodigo = incCodigo;
    }
    
    public String imprimirAldeoes(){
        String codigos = "{";
        for(Aldeao aldeao : this.mineradores){
            if(aldeao.getAcao() == AcaoAldeao.MINERANDO)
                codigos = codigos +  aldeao.getCodigo()+", ";
        }            
        codigos += "} ";
        for(Aldeao aldeao : this.mineradores){
            if(aldeao.getAcao() == AcaoAldeao.TRANSPORTANDO_OURO)
                codigos = codigos +  aldeao.getCodigo()+", ";
        }
        return codigos;
    }
    
}
