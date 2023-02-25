/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Entidades.enums.Poder;
import Entidades.enums.AcaoAldeao;
import Entidades.enums.AcaoTemplo;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tela.Principal;

/**
 *
 * @author wallisson
 */
public class Templo extends Thread {

    private int codigo;
    private static final String FUNCAO1 = "produção de oferendas de fé";
    private static final String FUNCAO2 = "sacrificar aldeao religioso";
    private static int fe = 0;
    private List<Aldeao> religiosos = new ArrayList<>();
    private Poder poder;
    private Poder evolucao;
    private Aldeao sacrificado;
    private Principal tela;
    private AcaoTemplo acao = AcaoTemplo.PARADO;
    private List<Fazenda> alvoF = new ArrayList<>();
    private List<MinaDeOuro> alvoM = new ArrayList<>();
    private List<Aldeao> alvoA = new ArrayList<>();
    //construtor, getter e setters
    public Templo(int codigo, Principal tela) {
        this.codigo = codigo;
        this.tela = tela;
        this.start();
    }

    public int getCodigo() {
        return codigo;
    }

    public static int getFe() {
        return fe;
    }

    public static void setFe(int fe) {
        Templo.fe = fe;
    }

    public List<Aldeao> getReligiosos() {
        return religiosos;
    }

    public void setReligiosos(List<Aldeao> religiosos) {
        this.religiosos = religiosos;
    }

    public Poder getPoder() {
        return poder;
    }

    public void setPoder(Poder poder) {
        this.poder = poder;
    }

    public Poder getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(Poder evolucao) {
        this.evolucao = evolucao;
    }

    public Aldeao getSacrificado() {
        return sacrificado;
    }

    public void setSacrificado(Aldeao sacrificado) {
        this.sacrificado = sacrificado;
    }

    public Principal getTela() {
        return tela;
    }

    public void setTela(Principal tela) {
        this.tela = tela;
    }

    public AcaoTemplo getAcao() {
        return acao;
    }

    public void setAcao(AcaoTemplo acao) {
        this.acao = acao;
    }

    //metodos
    public synchronized void sacrificar() {
        this.sacrificado.setAcao(AcaoAldeao.MORTO);
        Templo.setFe(Templo.fe + 100);
        //this.tela.mostrarAldeao(this.sacrificado.getCodigo(), this.sacrificado.getAcao().name());
        this.tela.removerAldeao(Integer.toString(this.sacrificado.getCodigo()), this.sacrificado.getAcao().name());
        //this.tela.removerMostraAldeao(this.sacrificado.getCodigo(), this.sacrificado.getAcao().name());
        this.tela.mostrarOferendaFe(Templo.fe);
        this.tela.mostrarMensagem("Concluída", "Aldeão sacrificado!");
        this.sacrificado = null;
        this.parar();
    }

    public void evoluir() {
        if (poder.isHabilitado()) {
            this.tela.mostrarMensagemErro("Erro", "Poder já está habilitado");
            return;
        }
        if (Templo.fe < poder.getCusto()) {
            this.tela.mostrarMensagemErro("Erro", "Não há oferendas de fé suficientes");
            return;
        }
        try {
            this.tela.mostrarMensagem("Iniciada", "Evoluindo poder...");
            Thread.sleep(poder.getTempo());
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Evolução de poder não concluída");
            Logger.getLogger(Templo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            poder.setHabilitado(true);
            Templo.fe -= poder.getCusto();
            this.tela.mostrarOferendaFe(fe);
            this.tela.mostrarMensagem("Concluída", "Evolução de poder Concluída!");
            this.parar();
        }
    }

    public boolean lancarNuvemGafanhotos() {
        int custo = 500;
        if (!Poder.NUVEM_GAFANHOTOS.isHabilitado()) {
            this.tela.mostrarMensagemErro("Erro", "O poder nuvem de gafanhotos não está habilitado");
            return false;
        }
        if (Templo.getFe() < custo) {
            this.tela.mostrarMensagemErro("Erro", "Não há oferendas fé suficientes!");
            return false;
        }
        List<Fazenda> nFazendas = new ArrayList<>();
        for (int i = 0; i < this.alvoF.size()/2; i++) {
            nFazendas.add(this.alvoF.get(i));
        }
        this.alvoF = nFazendas;
        this.tela.mostrarMensagem("Ataque", "Ataque Realizado!");
        return true;
    }

    public synchronized void parar() {
        try {
            this.acao = AcaoTemplo.PARADO;
            this.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(Templo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void agir() {
        this.notify();
    }

    public synchronized void interromper() {
        this.interrupt();
    }

    public String imprimirAldeoes() {
        String codigos = "";
        for (Aldeao aldeao : this.getReligiosos()) {
            codigos = codigos + aldeao.getCodigo() + ", ";
        }
        return codigos;
    }

    public void run() {
        while (true) {
            switch (this.acao) {
                case SACRIFICANDO:
                    this.sacrificar();
                    break;
                case EVOLUINDO:
                    this.evoluir();
                    break;
            }
        }
    }

}
