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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wallisson
 */
public class Fazenda {

    private int codigo;
    private String funcao = "produzir comida";
    private List<Aldeao> fazendeiros = new ArrayList<>();
    private static int capacidade = 5;
    private Semaphore espaco;
    private static int incCodigo = 1;

    public Fazenda(int codigo) {
        this.codigo = codigo;
        espaco = new Semaphore(capacidade, true);
    }

       
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public List<Aldeao> getFazendeiros() {
        return fazendeiros;
    }

    public void setFazendeiros(Aldeao aldeao) {
        this.fazendeiros.add(aldeao);
    }

    public static int getCapacidade() {
        return capacidade;
    }

    public static void setCapacidade(int capacidade) {
        Fazenda.capacidade = capacidade;
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
        Fazenda.incCodigo = incCodigo;
    }

    public String imprimirAldeoes(){
        String codigos = "{";
        for(Aldeao aldeao : this.fazendeiros){
            if(aldeao.getAcao() == AcaoAldeao.CULTIVANDO)
                codigos = codigos +  aldeao.getCodigo()+", ";
        }
        codigos += "}";
        for(Aldeao aldeao : this.fazendeiros){
            if(aldeao.getAcao() == AcaoAldeao.TRANSPORTANDO_COMIDA)
                codigos = codigos +  aldeao.getCodigo()+", ";
        }
        return codigos;
    }

 
    
    
    

    

  
    
    
    
    
}
