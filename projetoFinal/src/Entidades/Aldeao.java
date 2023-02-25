package Entidades;

import Entidades.enums.AcaoAldeao;
import static Entidades.enums.AcaoAldeao.CULTIVANDO;
import Entidades.enums.FuncaoAldeao;
import java.util.logging.Level;
import java.util.logging.Logger;
import tela.Principal;

/**
 *
 * @author wallisson
 */
public class Aldeao extends Thread {

    private int codigo;
    private FuncaoAldeao funcao = FuncaoAldeao.DESOCUPADO;
    private AcaoAldeao acao = AcaoAldeao.PARADO;
    private static int velocidade = 1;
    private static int rendimento = 1;
    private static int incCodigo = 1;
    private Prefeitura prefeitura;
    private Principal tela;
    private Fazenda fazenda;
    private MinaDeOuro mina;
    private Templo templo;
    private Maravilha maravilha;
    //private String funcao;

    //Contrutor, getters e setters
    public Aldeao(int codigo, Prefeitura prefeitura, Principal principal) {
        this.codigo = codigo;
        this.prefeitura = prefeitura;
        this.tela = principal;
    }

    public Prefeitura getPrefeitura() {
        return prefeitura;
    }

    public void setPrefeitura(Prefeitura prefeitura) {
        this.prefeitura = prefeitura;
    }

    public Principal getTela() {
        return tela;
    }

    public void setTela(Principal tela) {
        this.tela = tela;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public FuncaoAldeao getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoAldeao funcao) {
        this.funcao = funcao;
    }

    public AcaoAldeao getAcao() {
        return acao;
    }

    public void setAcao(AcaoAldeao acao) {
        this.acao = acao;
    }

    public static int getVelocidade() {
        return velocidade;
    }

    public static void setVelocidade(int velocidade) {
        Aldeao.velocidade = velocidade;
    }

    public static int getRendimento() {
        return rendimento;
    }

    public static void setRendimento(int rendimento) {
        Aldeao.rendimento = rendimento;
    }

    public static int getIncCodigo() {
        return incCodigo;
    }

    public static void setIncCodigo(int incCodigo) {
        Aldeao.incCodigo = incCodigo;
    }

    public Fazenda getFazenda() {
        return fazenda;
    }

    public void setFazenda(Fazenda fazenda) {
        this.fazenda = fazenda;
    }

    public MinaDeOuro getMina() {
        return mina;
    }

    public void setMina(MinaDeOuro mina) {
        this.mina = mina;
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
    
    

    //-----------------------Acoes---------------------------
    public void construirFazenda() {
        int tempo = 30 * Prefeitura.getHora();
        int custoComida = 100;
        int custoOuro = 500;
        Fazenda fazenda;
        if (Prefeitura.getComida() < custoComida || Prefeitura.getOuro() < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoAldeao.PARADO);
            return;
        }
        try {
            System.out.println("Construindo fazenda...");
            this.tela.mostrarMensagem("Iniciada", "Construindo fazenda...");
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Criação de fazenda não concluída!");
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fazenda = new Fazenda(Fazenda.getIncCodigo());
            prefeitura.getFazendas().add(fazenda);
            Prefeitura.setComida(Prefeitura.getComida() - custoComida);
            Prefeitura.setOuro(Prefeitura.getComida() - custoOuro);
            System.out.println("Fazenda construída!");
            this.tela.mostrarMensagem("Concluída", "Construção de fazenda concluída!");
            this.tela.adicionarFazenda(Integer.toString(prefeitura.getFazendas().size()), "");
            this.tela.mostrarFazenda(prefeitura.getFazendas().size(), "");
            this.tela.mostrarComida(Prefeitura.getComida());
            this.tela.mostrarOuro(Prefeitura.getOuro());
            this.acao = AcaoAldeao.PARADO;
            this.tela.mostrarAldeao(this.codigo, this.acao.name());
            Fazenda.setIncCodigo(Fazenda.getIncCodigo() + 1);
        }
    }

    public void construirMinaDeOuro() {
        int tempo = 40 * Prefeitura.getHora();
        int custoComida = 1000;
        int custoOuro = 0;
        MinaDeOuro mina;
        if (Prefeitura.getComida() < custoComida || Prefeitura.getOuro() < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoAldeao.PARADO);
            return;
        }
        try {
            System.out.println("Construindo mina de ouro...");
            this.tela.mostrarMensagem("Iniciada", "Construindo mina...");
            Thread.sleep(tempo);

        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Criação de mina de ouro não concluída!");
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mina = new MinaDeOuro(MinaDeOuro.getIncCodigo());
            this.prefeitura.getMinasDeOuro().add(mina);
            Prefeitura.setComida(Prefeitura.getComida() - custoComida);
            Prefeitura.setOuro(Prefeitura.getOuro() - custoOuro);
            System.out.println("Mina de ouro construída!");
            this.tela.mostrarMensagem("Concluída", "Construção de mina de ouro concluída!");
            this.tela.adicionarMinaOuro(Integer.toString(prefeitura.getMinasDeOuro().size()), "");
            this.tela.mostrarMinaOuro(prefeitura.getMinasDeOuro().size(), "");
            this.tela.mostrarComida(Prefeitura.getComida());
            this.tela.mostrarOuro(Prefeitura.getOuro());
            this.acao = AcaoAldeao.PARADO;
            this.tela.mostrarAldeao(this.codigo, this.acao.name());
            MinaDeOuro.setIncCodigo(MinaDeOuro.getIncCodigo() + 1);
        }
    }

    public void construirTemplo() {
        int tempo = 100 * Prefeitura.getHora();
        int custoComida = 2000;
        int custoOuro = 2000;
        Templo templo;
        if (Prefeitura.getComida() < custoComida || Prefeitura.getOuro() < custoOuro) {
            this.tela.mostrarMensagemErro("Erro", "Quantidade de comida ou ouro insuficientes!");
            this.setAcao(AcaoAldeao.PARADO);
            return;
        }
        try {
            templo = new Templo(1, this.tela);
            this.prefeitura.setTemplo(templo);
            System.out.println("Construindo templo...");
            this.tela.mostrarMensagem("Iniciada", "Construindo templo...");
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            this.tela.mostrarMensagemErro("Erro", "Criação de templo não concluída!");
            templo = null;
            this.prefeitura.setTemplo(null);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            templo = new Templo(1, this.tela);          
            Prefeitura.setComida(Prefeitura.getComida() - custoComida);
            Prefeitura.setOuro(Prefeitura.getOuro() - custoOuro);
            System.out.println("Templo construído!");
            this.tela.mostrarMensagem("Concluída", "Construção do Templo concluída!");
            this.tela.mostrarComida(Prefeitura.getComida());
            this.tela.mostrarOuro(Prefeitura.getOuro());
            this.setAcao(AcaoAldeao.PARADO);
            this.tela.mostrarAldeao(this.codigo, this.acao.name());
        }

    }

    public void cultivar() {
        int producao = 10 * Aldeao.rendimento;
        int tempoProducao = 1 * Prefeitura.getHora();
        int transporte = 2 * Prefeitura.getHora() / Aldeao.getVelocidade();

        this.acao = AcaoAldeao.CULTIVANDO;
        this.tela.mostrarAldeao(this.codigo, this.acao.name());        
        this.tela.mostrarFazenda(this.fazenda.getCodigo(), this.fazenda.imprimirAldeoes());
        try {
            System.out.println("aldeao " + this.codigo + " cultivando...");
            fazenda.getEspaco().acquire(); //ocupa um lugar no espaco da fazenda
            Thread.sleep(tempoProducao);
        } catch (InterruptedException ex) {
            this.fazenda.getFazendeiros().remove(this);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fazenda.getEspaco().release(); // sai do espaco da fazenda
            this.acao = AcaoAldeao.TRANSPORTANDO_COMIDA;            
        }
        try {
            System.out.println("aldeao " + this.codigo + " transportando comida...");
            this.tela.mostrarAldeao(this.codigo, this.acao.name()); 
            this.tela.mostrarFazenda(this.fazenda.getCodigo(), this.fazenda.imprimirAldeoes());
            Thread.sleep(transporte);
        } catch (InterruptedException ex) {
            this.fazenda.getFazendeiros().remove(this);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Prefeitura.setComida(Prefeitura.getComida() + producao);
            this.tela.mostrarComida(Prefeitura.getComida());
        }
    }

    public void minerar() {
        int producao = 5 * Aldeao.rendimento;
        int tempoProducao = 2 * Prefeitura.getHora();
        int transporte = 3 * Prefeitura.getHora() / Aldeao.velocidade;
        
        this.acao = AcaoAldeao.MINERANDO;
        this.tela.mostrarAldeao(this.codigo, this.acao.name());       
        this.tela.mostrarMinaOuro(this.mina.getCodigo(), this.mina.imprimirAldeoes());
        try {
            System.out.println("aldeao " + this.codigo + " minerando...");
            this.mina.getEspaco().acquire();
            Thread.sleep(tempoProducao);
        } catch (InterruptedException ex) {
            this.mina.getMineradores().remove(this);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.mina.getEspaco().release();
            this.acao = AcaoAldeao.TRANSPORTANDO_OURO;          
        }
        try {
            System.out.println("aldeao " + this.codigo + " transportando minério de ouro...");
            this.tela.mostrarAldeao(this.codigo, this.acao.name()); 
            this.tela.mostrarMinaOuro(this.mina.getCodigo(), this.mina.imprimirAldeoes());
            Thread.sleep(transporte);
        } catch (InterruptedException ex) {
            this.mina.getMineradores().remove(this);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Prefeitura.setOuro(Prefeitura.getOuro() + producao);
            this.tela.mostrarOuro(Prefeitura.getOuro());
        }
    }

    public void orar() {
        int producao = 5;
        int tempoProducao = 5 * Prefeitura.getHora();
        this.tela.mostrarAldeao(this.codigo, this.acao.name());
        this.templo.getReligiosos().add(this);
        try {
            System.out.println("aldeao " + this.codigo + " orando...");
            Thread.sleep(tempoProducao);
        } catch (InterruptedException ex) {
            this.templo.getReligiosos().remove(this);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Templo.setFe(Templo.getFe()+producao);
            this.tela.mostrarOferendaFe(Templo.getFe());
        }
    }
    
    public void connstruirMaravilha() {
        int producao = 1;
        int tempoProducao = 10 * Prefeitura.getHora();
        this.tela.mostrarAldeao(this.codigo, this.acao.name());
        this.maravilha.getConstrutores().add(this);
        try {
            System.out.println("aldeao " + this.codigo + " construindo maravilha...");
            Thread.sleep(tempoProducao);
        } catch (InterruptedException ex) {
            this.maravilha.getConstrutores().remove(this);
            Logger.getLogger(Aldeao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.maravilha.setTijolos(this.maravilha.getTijolos()+1);
            Prefeitura.setComida(Prefeitura.getComida() - 1);
            Prefeitura.setOuro(Prefeitura.getOuro() - 1);
            this.tela.mostrarComida(Prefeitura.getComida());
            this.tela.mostrarOuro(Prefeitura.getOuro());
            this.tela.mostrarMaravilha(this.maravilha.getTijolos());        
        }
    }

    public synchronized void parar() {
        try {
            this.acao = AcaoAldeao.PARADO;
            this.tela.mostrarAldeao(this.codigo, prefeitura.getAldeoes().get(this.codigo-1).getAcao().name());
            this.wait();
        } catch (InterruptedException e) {
        }

    }

    public synchronized void agir() {
        try {
            this.notify();
        } catch (Exception e) {
        }
    }
    
    public synchronized void interromper(){
        this.acao = AcaoAldeao.MORTO;
        this.agir();
    }

    @Override
    public void run() {
        while (this.acao != AcaoAldeao.MORTO) {
            switch (this.acao) {
                case CULTIVANDO:
                    this.fazenda.getFazendeiros().add(this);
                    while (this.acao == AcaoAldeao.CULTIVANDO || this.acao == AcaoAldeao.TRANSPORTANDO_COMIDA) {                       
                        this.cultivar();
                    }
                    this.fazenda.getFazendeiros().remove(this);
                    break;
                case MINERANDO:
                    this.mina.getMineradores().add(this);
                    while (this.acao == AcaoAldeao.MINERANDO || this.acao == AcaoAldeao.TRANSPORTANDO_OURO) {
                        this.minerar();
                    }
                    this.mina.getMineradores().remove(this);
                    break;
                case ORANDO:                    
                    while (this.acao == AcaoAldeao.ORANDO) {
                        this.orar();
                    }                   
                    break;  
                case CONSTRUINDO_FAZENDA:
                    this.construirFazenda();
                    break;
                case CONSTRUINDO_MINA:
                    this.construirMinaDeOuro();
                    break;
                case CONSTRUINDO_TEMPLO:
                    this.construirTemplo();
                    break;
                case CONSTRUINDO_MARAVILHA:
                    while (this.acao == AcaoAldeao.CONSTRUINDO_MARAVILHA && this.maravilha.getTijolos()<=this.maravilha.getFim()) {
                        this.connstruirMaravilha();
                    }
                    if (this.maravilha.getTijolos()<=this.maravilha.getFim()){
                        this.tela.mostrarMensagem("Fim de jogo", "Parabéns você venceu!");
                        System.exit(0);
                    }    
                    break;
            }
        }

    }
}
