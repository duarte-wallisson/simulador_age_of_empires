package tela;

import Entidades.Prefeitura;
import Entidades.enums.Poder;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tela.enumerador.SituacaoInicio;
import tela.enumerador.SituacaoJogador;

public class ClienteTCP extends Thread implements Serializable {

    private transient Socket conexao;
    private transient ObjectOutputStream saida;
    private transient ObjectInputStream entrada;
    private transient ClienteTCP clienteRecebe;
    private transient boolean escutando;

    private transient List<ClienteTCP> jogadores;

    private transient Principal tela;
    private transient Prefeitura prefeitura;
    private String ip;
    private String nome;
    private String civilizacao;

    private SituacaoJogador situacaoSala = SituacaoJogador.NADA;
    private SituacaoJogador situacaoJogo = SituacaoJogador.NADA;

    public ClienteTCP(Principal tela, Prefeitura prefeitura) {
        this.tela = tela;
        this.prefeitura = prefeitura;
        this.ip = "localhost";
    }

    private ClienteTCP(Socket conexao, Principal tela, Prefeitura prefeitura) {
        this.conexao = conexao;
        this.tela = tela;
        this.prefeitura = prefeitura;
        this.escutando = true;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCivilizacao() {
        return civilizacao;
    }

    public void setCivilizacao(String civilizacao) {
        this.civilizacao = civilizacao;
    }

    public SituacaoJogador getSituacaoSala() {
        return situacaoSala;
    }

    public void setSituacaoSala(SituacaoJogador situacaoSala) {
        this.situacaoSala = situacaoSala;
    }

    public SituacaoJogador getSituacaoJogo() {
        return situacaoJogo;
    }

    public void setSituacaoJogo(SituacaoJogador situacaoJogo) {
        this.situacaoJogo = situacaoJogo;
    }

//----------ESCUTAR MENSAGENS-------------
    public void run() {
        while (true) {
            String mensagem;
            ClienteTCP jogador;
            ObjectInputStream entrada = null;
            try {
                try {
                    entrada = new ObjectInputStream(this.conexao.getInputStream());
                    this.jogadores = (List<ClienteTCP>) entrada.readObject(); // 1 recebimento
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (ClienteTCP j : this.jogadores) { //adiciona os jogadores já existentes na tela do cliente
                    this.tela.adicionarJogador(j.getNome(), j.getCivilizacao(), j.getIp(), j.getSituacaoJogo().name()+", "+j.getSituacaoSala());
                }

                while (this.escutando) {
                    mensagem = (String) entrada.readObject();
                    if ("/addPlayer".equals(mensagem)) {
                        jogador = (ClienteTCP) entrada.readObject();
                        this.tela.adicionarJogador(jogador.nome, jogador.civilizacao, jogador.ip, jogador.getSituacaoJogo().name()+", "+jogador.getSituacaoSala());
                    }
                    if ("/rmvPlayer".equals(mensagem)) {
                        this.tela.removerJogador((int) entrada.readObject());
                    }
                    if ("/startGame".equals(mensagem)) {
                        this.situacaoJogo = SituacaoJogador.JOGANDO;
                        this.prefeitura.iniciar();
                        this.tela.getTpJogo().setSelectedIndex(1);
                        for(ClienteTCP inimigo : this.jogadores){
                            this.tela.adicionarInimigo(this.nome+"-"+this.civilizacao);
                        }
                        if (this.tela.getSituacaoInicio() != SituacaoInicio.CRIAR_INICIADO) {
                            this.tela.setSituacaoInicio(SituacaoInicio.CONECTADO_INICIADO);
                            this.tela.habilitarInicio();
                        }
                    }
                    if ("/endGame".equals(mensagem)) {
                        this.situacaoJogo = SituacaoJogador.AGUARDANDO;
                        if (this.tela.getSituacaoInicio() != SituacaoInicio.CRIAR_INICIADO) {
                            this.prefeitura.interromperThreads();
                            this.tela.getTpJogo().setSelectedIndex(0);
                            this.tela.setSituacaoInicio(SituacaoInicio.CONECTADO);
                            this.tela.habilitarInicio();
                        }
                    }
                    if ("/destroyGame".equals(mensagem)) {
                        System.exit(0);
                    }
                    /*if (("/attackNGF ("+this.nome+"-"+this.civilizacao+")").equals(mensagem)) {
                        if(Poder.PROTECAO_NUVEM_GAFANHOTOS.isHabilitado()){
                            this.saida.writeObject(true);
                            this.saida.writeObject(this.prefeitura.getFazendas());
                        } else {
                            this.saida.writeObject(false);
                        }
                    }*/
                    this.tela.adicionarMensagem(mensagem);
                }
                entrada.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    //--------------CRIAR JOGO------------
    boolean levantou = false;
    private int porta = 12345;
    public ServerSocket servidor;

    public boolean criarJogo(String nome, String civilizacao) throws InterruptedException {
        this.situacaoSala = SituacaoJogador.CRIADOR;
        this.situacaoJogo = SituacaoJogador.AGUARDANDO;
        Thread tServidor;
        tServidor = new Thread(new Runnable() {
            public void run() {
                porta = 12345;
                List<ObjectOutputStream> saidas = new ArrayList<ObjectOutputStream>();
                List<ClienteTCP> jogadores = new ArrayList<ClienteTCP>();
                try {
                    InetAddress addr = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
                    @SuppressWarnings("resource")
                    ServerSocket servidor1 = new ServerSocket(porta, 50, addr);
                    System.out.println(servidor1.getInetAddress().getHostAddress());
                    //servidor =  servidor1;
                    if (servidor1.isBound()) {
                        levantou = true;
                        while (true) {
                            Socket conexao = servidor1.accept();
                            (new ServidorTCP(conexao, saidas, jogadores)).start();
                        }
                    } else {
                        porta++;
                        levantou = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        tServidor.start();
        Thread.sleep(500);
        if (levantou) {
            try {
                this.ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
                Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.conectar(this.tela.getTfNome().getText(), this.tela.getCbCivilizacoes().getSelectedItem().toString(), this.ip);
            return true;
        } else {
            this.tela.mostrarMensagemErro("Erro", "Servidor não levantado! Tente novamente!");
            return false;
        }
    }

    public void iniciarJogo() {
        try {

                this.saida.writeObject("/startGame");
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destruirJogo() {
        try {
            this.saida.writeObject("/destroyGame");
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void encerrarJogo() {
        //encerra quem comandou, depois envia para as outras encerrar
        this.prefeitura.interromperThreads();
        this.tela.getTpJogo().setSelectedIndex(0);
        try {
            this.saida.writeObject("/endGame");
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean conectar(String nome, String civilizacao, String host) {
        this.nome = nome;
        this.civilizacao = civilizacao;
        this.situacaoJogo = SituacaoJogador.AGUARDANDO;
        if(this.situacaoSala != SituacaoJogador.CRIADOR)
            this.situacaoSala = SituacaoJogador.CONVIDADO;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.conexao = new Socket(host, 12345);
            if (this.conexao.isConnected()) {
                this.saida = new ObjectOutputStream(this.conexao.getOutputStream());
                this.saida.writeObject(this); // 2 envia-se para a lista de jogadores

                this.clienteRecebe = new ClienteTCP(conexao, this.tela, this.prefeitura);
                this.clienteRecebe.start();
                return true;
           } else {
               return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public void desconectar() {
        try {
            this.saida.writeObject("/desconect");
            this.clienteRecebe.escutando = false;
            this.saida.close();
            this.conexao.close();
            this.tela.limparJogadores();
            this.tela.limparMensagens();
            this.jogadores = null;
            if (this.situacaoJogo == SituacaoJogador.JOGANDO) {
                this.prefeitura.interromperThreads();
            }
            this.situacaoJogo = SituacaoJogador.NADA;

            this.tela.mostrarMensagem("Informação", "Jogo encerrado ou desconectado!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            this.saida.writeObject(mensagem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagemDesconexao(String mensagem) {
        try {
            this.saida.writeObject(mensagem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarAtaque(String poder, String inimigo) {
        switch (poder) {
            case "Nuvem de gafanhotos":
                try {
                    this.saida.writeObject("/attackNGF ("+ inimigo +")");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            break;    
        }
    }

}
