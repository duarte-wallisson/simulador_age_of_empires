/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Entidades.enums.AcaoAldeao;
import Entidades.enums.AcaoPrefeitura;
import Entidades.enums.Poder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tela.Principal;

/**
 *
 * @author wallisson
 */
public class Prefeitura extends Thread {

    private final int codigo = 1;
    private static final String funcao = "administrar ouro e comida; criar aldeoes e evoluir";
    private static int comida = 15000;
    private static int ouro = 10000;
    private List<Aldeao> aldeoes = new ArrayList<>();
    private List<Fazenda> fazendas = new ArrayList<>();
    private List<MinaDeOuro> minasDeOuro = new ArrayList<>();
    private Templo templo;
    private Maravilha maravilha = new Maravilha();
    private AcaoPrefeitura acao = AcaoPrefeitura.PARADA;
    private Principal tela;
    private static int hora = 50;

    public Prefeitura(Principal principal) {
        this.tela = principal;
        this.start();
    }

    public void iniciar() {
        this.setComida(15000);
        this.setOuro(10000);
        this.tela.mostrarComida(this.getComida());
        this.tela.mostrarOuro(this.getOuro());
        for (int i = 0; i < 5; i++) {
            Aldeao.setIncCodigo(i + 1);
            this.getAldeoes().add(new Aldeao(Aldeao.getIncCodigo(), this, this.tela));
            this.getAldeoes().get(i).start();
            this.tela.adicionarAldeao(Integer.toString(Aldeao.getIncCodigo()), this.getAldeoes().get(i).getAcao().name());
            this.tela.mostrarAldeao(Aldeao.getIncCodigo(), this.getAldeoes().get(i).getAcao().name());
        }
        Aldeao.setIncCodigo(Aldeao.getIncCodigo() + 1);
        this.getFazendas().add(new Fazenda(Fazenda.getIncCodigo()));
        this.tela.adicionarFazenda(Integer.toString(Fazenda.getIncCodigo()), "");
        this.tela.mostrarFazenda(Fazenda.getIncCodigo(), "");
        Fazenda.setIncCodigo(Fazenda.getIncCodigo() + 1);
        this.getFazendas().add(new Fazenda(Fazenda.getIncCodigo()));
        this.tela.adicionarFazenda(Integer.toString(Fazenda.getIncCodigo()), "");
        this.tela.mostrarFazenda(Fazenda.getIncCodigo(), "");
        Fazenda.setIncCodigo(Fazenda.getIncCodigo() + 1);
        this.getMinasDeOuro().add(new MinaDeOuro(MinaDeOuro.getIncCodigo()));
        this.tela.adicionarMinaOuro(Integer.toString(MinaDeOuro.getIncCodigo()), "");
        this.tela.mostrarMinaOuro(MinaDeOuro.getIncCodigo(), "");
        MinaDeOuro.setIncCodigo(MinaDeOuro.getIncCodigo() + 1);
        this.tela.mostrarOferendaFe(Templo.getFe());
        this.tela.mostrarPrefeitura("eeee", Color.ORANGE);
        this.tela.habilitarTemplo();
        this.tela.habilitarMaravilha();
        this.tela.mostrarMaravilha(2000);

    }

    public Principal getTela() {
        return tela;
    }

    public void setTela(Principal tela) {
        this.tela = tela;
    }

    public static int getComida() {
        return comida;
    }

    public static void setComida(int comida) {
        Prefeitura.comida = comida;
    }

    public static int getOuro() {
        return ouro;
    }

    public static void setOuro(int ouro) {
        Prefeitura.ouro = ouro;
    }

    public List<Aldeao> getAldeoes() {
        return aldeoes;
    }

    public void setAldeoes(List<Aldeao> aldeoes) {
        this.aldeoes = aldeoes;
    }

    public List<Fazenda> getFazendas() {
        return fazendas;
    }

    public void setFazendas(List<Fazenda> fazendas) {
        this.fazendas = fazendas;
    }

    public List<MinaDeOuro> getMinasDeOuro() {
        return minasDeOuro;
    }

    public void setMinasDeOuro(List<MinaDeOuro> minasDeOuro) {
        this.minasDeOuro = minasDeOuro;
    }

    public Templo getTemplo() {
        return templo;
    }

    public void setTemplo(Templo templo) {
        this.templo = templo;
    }

    public Maravilha getMaravilha() {
        return maravilha;
    }

    public void setMaravilha(Maravilha maravilha) {
        this.maravilha = maravilha;
    }

    public static int getHora() {
        return hora;
    }

    public static void setHora(int hora) {
        Prefeitura.hora = hora;
    }

    public AcaoPrefeitura getAcao() {
        return acao;
    }

    public void setAcao(AcaoPrefeitura acao) {
        this.acao = acao;
    }

    //---------------------------------------ACOES-------------------------------------------
    public synchronized void criarAldeao() {
        int tempo = 20 * this.hora;
        int custoComida = 100;
        int custoOuro = 0;
        if (Prefeitura.comida < custoComida || Prefeitura.ouro < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }

        try {
            System.out.println("Criando aldeão...");
            this.tela.mostrarMensagem("Iniciada", "Criando aldeão...");
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Criação de Aldeão não concluída!");
            Logger.getLogger(Prefeitura.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } finally {
            this.aldeoes.add(new Aldeao(Aldeao.getIncCodigo(), this, this.tela));
            this.getAldeoes().get(Aldeao.getIncCodigo() - 1).start();
            this.tela.mostrarComida(this.getComida());
            this.tela.mostrarOuro(this.getOuro());
            System.out.println("Criação do aldeão concluída!"); // mudar pra janela  
            this.tela.mostrarMensagem("Concluída", "Criação de aldeão concluída!");
            this.tela.adicionarAldeao(Integer.toString(Aldeao.getIncCodigo()), this.getAldeoes().get(this.getAldeoes().size() - 1).getAcao().name());
            this.tela.mostrarAldeao(this.getAldeoes().size() - 1, this.getAldeoes().get(this.getAldeoes().size() - 1).getAcao().name());
            this.tela.mostrarComida(Prefeitura.getComida());
            this.tela.mostrarOuro(Prefeitura.getOuro());
            Aldeao.setIncCodigo(Aldeao.getIncCodigo() + 1);
            this.parar();
        }
    }

    public synchronized void evoluirAldeao() {
        int tempo = 100 * this.hora;
        int custoComida = 5000;
        int custoOuro = 5000;
        if (Aldeao.getRendimento() == 2 && Aldeao.getVelocidade() == 2) {
            this.tela.mostrarMensagemErro("Erro", "Aldeões já evoluídos!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }
        if (Prefeitura.comida < custoComida || Prefeitura.ouro < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }
        try {
            System.out.println("evoluindo aldeão...");
            this.tela.mostrarMensagem("Iniciada", "Evoluindo aldeões...");
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Evolução de Aldeão não concluída!");
            Logger.getLogger(Prefeitura.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } finally {
            Aldeao.setRendimento(Aldeao.getRendimento() * 2);
            Aldeao.setVelocidade(Aldeao.getVelocidade() * 2);
            this.tela.mostrarComida(this.getComida());
            this.tela.mostrarOuro(this.getOuro());
            System.out.println("Produção de ouro, comida e transporte dobradas!");
            System.out.println("Evolução de Aldeão concluída!");
            this.tela.mostrarMensagem("Concluída", "Produção de ouro, comida e transporte dobradas!");
            this.tela.mostrarComida(Prefeitura.getComida());
            this.tela.mostrarOuro(Prefeitura.getOuro());
            this.setAcao(AcaoPrefeitura.PARADA);
        }
    }

    public synchronized void evoluirFazenda() {
        int tempo = 100 * this.hora;
        int custoComida = 500;
        int custoOuro = 5000;
        if (Fazenda.getCapacidade() == 10) {
            this.tela.mostrarMensagemErro("Erro", "Fazendas já evoluídas!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }
        if (Prefeitura.comida < custoComida || Prefeitura.ouro < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }
        try {
            System.out.println("evoluindo fazendas...");
            this.tela.mostrarMensagem("Iniciada", "Evoluindo fazendas...");
            Thread.sleep(tempo);
            System.out.print("Capacidade de fazendeiros aumentada de " + Fazenda.getCapacidade() + " para ");
            Fazenda.setCapacidade(Fazenda.getCapacidade() * 2);
            System.out.print(Fazenda.getCapacidade());
            System.out.println("Evolução de fazendas concluída!");
            this.setAcao(AcaoPrefeitura.PARADA);
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Evolução de Fazenda não concluída!");
            Logger.getLogger(Prefeitura.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } finally {
            System.out.print("Capacidade de fazendeiros aumentada de " + Fazenda.getCapacidade() + " para ");
            Fazenda.setCapacidade(Fazenda.getCapacidade() * 2);
            System.out.print(Fazenda.getCapacidade());
            this.tela.mostrarMensagem("Concluída", "Capacidade de fazendeiros aumentada para " + Fazenda.getCapacidade());
            this.tela.mostrarComida(this.getComida());
            this.tela.mostrarOuro(this.getOuro());
            System.out.println("Evolução das fazendas concluída!");
            this.tela.mostrarComida(this.getComida());
            this.tela.mostrarOuro(this.getOuro());
        }

    }

    public synchronized void evoluirMinaDeOuro() {
        int tempo = 100 * this.hora;
        int custoComida = 2000;
        int custoOuro = 1000;

        if (MinaDeOuro.getCapacidade() == 10) {
            this.tela.mostrarMensagemErro("Erro", "Minas já evoluídas!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }
        if (Prefeitura.comida < custoComida || Prefeitura.ouro < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoPrefeitura.PARADA);
            return;
        }
        try {
            this.tela.mostrarMensagem("Iniciada", "Evoluindo minas de ouro...");
            System.out.println("evoluindo minas de ouro...");
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Evolução de Mina não concluída!");
            Logger.getLogger(Prefeitura.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } finally {
            System.out.print("Capacidade de mineiros aumentada de " + MinaDeOuro.getCapacidade() + " para ");
            Fazenda.setCapacidade(MinaDeOuro.getCapacidade() * 2);
            System.out.print(MinaDeOuro.getCapacidade());
            this.tela.mostrarMensagem("Concluída", "Capacidade de mineiros aumentada: " + MinaDeOuro.getCapacidade());
            this.comida -= custoComida;
            this.ouro -= custoOuro;
            System.out.println("Evolução das minas concluída!");
            this.tela.mostrarComida(this.getComida());
            this.tela.mostrarOuro(this.getOuro());
            this.setAcao(AcaoPrefeitura.PARADA);
        }
    }

    public synchronized void parar() {
        try {
            this.acao = AcaoPrefeitura.PARADA;
            this.wait();
        } catch (Exception e) {
        }

    }

    public synchronized void agir() {
        try {
            this.notify();
        } catch (Exception e) {
        }
    }
    
    public synchronized void interromper(){
        this.interrupt();
    }

    public synchronized void interromperThreads() {
        //Interromper todas as Threads durante o jogo
        if (this.aldeoes != null) {
            for (Aldeao aldeao : this.aldeoes) {
                aldeao.interromper();
                System.out.println(aldeao.getState());
            }
        }
        if (this.templo != null) {
            this.templo.interromper();
            System.out.println(this.templo.getState());
        }
        //Limpar a tela
        this.aldeoes = new ArrayList<>();
        this.tela.limparAldeoes(0);      
        this.fazendas = new ArrayList<>();
        this.tela.limparFazendas(0);
        this.minasDeOuro = new ArrayList<>();
        this.tela.limparMinasOuro(0);
        
        
        //Templo
        Templo.setFe(0);
        this.templo = null;
        this.tela.desabilitarTemplo();
        this.tela.mostrarOferendaFe(Templo.getFe());
        
        //Maravilha
        this.maravilha.setTijolos(0);
        this.tela.desabilitarMaravilha();
        this.tela.mostrarMaravilha(this.maravilha.getTijolos());
        
        //ver a habilitação de poderes na Principal
        Poder.CHUVA_PEDRAS.setHabilitado(false);
        Poder.MORTE_PRIMOGENITOS.setHabilitado(false);
        Poder.NUVEM_GAFANHOTOS.setHabilitado(false);
        Poder.PROTECAO_CHUVA_PEDRAS.setHabilitado(false);
        Poder.PROTECAO_MORTE_PRIMOGENITOS.setHabilitado(false);
        Poder.PROTECAO_NUVEM_GAFANHOTOS.setHabilitado(false);
        
        Aldeao.setIncCodigo(1);
        Aldeao.setRendimento(1);
        Aldeao.setVelocidade(1);
        Fazenda.setIncCodigo(1);
        Fazenda.setCapacidade(5);
        MinaDeOuro.setIncCodigo(1);
        MinaDeOuro.setCapacidade(5);
        
    }
    
   

    public void run() {
        while (true) {
            switch (this.acao) {
                case PARADA:
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Prefeitura.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case CRIANDO_ALDEAO:
                    this.criarAldeao();
                    break;
                case EVOLUINDO_ALDEAO:
                    this.evoluirAldeao();
                    break;
                case EVOLUINDO_FAZENDA:
                    this.evoluirFazenda();
                    break;
                case EVOLUINDO_MINA:
                    this.evoluirMinaDeOuro();
                    break;
            }
        }
    }
}
