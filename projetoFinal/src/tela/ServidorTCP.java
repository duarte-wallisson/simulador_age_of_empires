package tela;

import Entidades.Fazenda;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCP extends Thread {

    private Socket conexao;
    private List<ObjectOutputStream> saidas;

    private List<ClienteTCP> jogadores;

    public ServidorTCP(Socket conexao, List<ObjectOutputStream> saidas, List<ClienteTCP> jogadores) {
        System.out.println("Cliente conectado: " + conexao.getInetAddress().getHostAddress());
        this.conexao = conexao;
        this.saidas = saidas;
        this.jogadores = jogadores;
    }

    public List<ClienteTCP> getJogadores() {
        return jogadores;
    }

    public void run() {
        DateFormat formato = new SimpleDateFormat("HH:mm");
        try {
            ObjectInputStream entrada = new ObjectInputStream(this.conexao.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(this.conexao.getOutputStream());
            ClienteTCP jogador;
            String mensagem; // primeira execução do ".writeObject" no comando Cliente.conectar

            jogador = (ClienteTCP) entrada.readObject(); //recebe o jogador que acabou de conectar-se
            super.setName(jogador.getNome());
            synchronized (this.saidas) {
                for (ObjectOutputStream saida1 : this.saidas) {
                    saida1.writeObject("/addPlayer");
                    saida1.writeObject(jogador);
                }
                this.saidas.add(saida);
            }

            this.jogadores.add(jogador); //adiciona o novo jogador na lista de jogadores
            saida.writeObject(this.jogadores); //envia a lista de jogadores no jogo      
            try {
                while (!(mensagem = (String) entrada.readObject()).equals("/desconect")) {
                    System.out.println("dentro --> [" + mensagem + "]");
                    synchronized (this.saidas) {
                        if (mensagem.contains("/endGame")) {
                            for (ObjectOutputStream saida3 : this.saidas) {
                                if(saida3 != saida)
                                    saida3.writeObject(mensagem);
                            }
                        } else if (mensagem.contains("/")) {
                            for (ObjectOutputStream saida3 : this.saidas) {
                                saida3.writeObject(mensagem);
                            }

                            /*if (mensagem.equals("/attackNFG")) {
                                for (ObjectOutputStream saida3 : this.saidas.) {
                                    saida.
                                    saida3.writeObject(jogador.getNome()+"-"+jogador.getCivilizacao());
                                }
                                /*boolean protecao = (boolean)entrada.readObject();
                                if(protecao){
                                    List<Fazenda> alvoF = (List<Fazenda>)entrada.readObject();
                                    saida.writeObject(alvoF);
                                } else {
                                    saida.writeObject("Alvo está com proteção habilitada");
                                }
                            }*/
                        } else {

                            String msg = super.getName() + "(" + jogador.getCivilizacao() + ")(" + formato.format(new Date()) + "): " + mensagem;
                            for (ObjectOutputStream saida3 : this.saidas) {
                                saida3.writeObject(msg);
                                // }
                            }
                        }
                    }
                }
                synchronized (this.saidas) {
                    int i = 1;
                    for (ObjectOutputStream saida2 : this.saidas) {
                        saida2.writeObject("/rmvPlayer");
                    }
                    for (ObjectOutputStream saida2 : this.saidas) {
                        if (saida2 != saida) {
                            i++;
                        }
                    }
                    for (ObjectOutputStream saida2 : this.saidas) {
                        saida2.writeObject(i);
                    }
                    System.out.println("fora --> [" + mensagem + "]");
                }
            } catch (SocketException e) {
            } // Fechado no clinte sem desconectar
            synchronized (this.saidas) {
                this.saidas.remove(saida);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
            saida.close();
            entrada.close();
            System.out.println("Cliente desconectado: " + conexao.getInetAddress().getHostAddress());
            this.conexao.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
