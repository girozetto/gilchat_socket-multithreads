import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Cliente {
    private final int PORTA = 5500 ;
    private final String ENDERECO = "localhost" ;

    private Socket cliente;
    private String id;
    
    public Cliente() throws UnknownHostException, IOException {
        this.cliente = new Socket(this.ENDERECO, this.PORTA);
        JOptionPane.showMessageDialog(null,"Conectado com sucesso");
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Cliente(Socket cliente){
        this.cliente = cliente;
    }

    public void iniciarEspera(Actualizacao act)
    {
        new LeituraThread(new Leitura(this, act)).start();
    }
    public Socket getCliente() {
        return cliente;
    }
    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public void fecharConexao() throws IOException
    {
        this.cliente.close();
    }

    public void enviarMensagem(Mensagem msg) throws IOException
    {
        PrintWriter escritor = new PrintWriter(this.cliente.getOutputStream(), true);
        escritor.printf("%s %s%n",msg.getIdEmissor(),msg.getConteudo().replaceAll(" ", "-"));
    }

    public Mensagem esperandoMensagem() throws IOException
    {
        BufferedReader leitor = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
        String[] msg = leitor.readLine().split(" ");
        return new Mensagem(msg[0], msg[1].replaceAll("-", " "));
    }
}
