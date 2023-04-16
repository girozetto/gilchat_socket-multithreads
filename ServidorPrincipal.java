import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorPrincipal implements EsperaServ, Actualizacao{
    public static final int PORTA = 5500;
    final private static int MAXCHAR = 8;

    private ServerSocket servidor;
    private List<Cliente> usuarios;
    public ServidorPrincipal() throws IOException{
        this.servidor = new ServerSocket(PORTA);
        this.usuarios = new ArrayList<>();
    }

    public void iniciarServidor()
    {
        new Thread(new EsperarConexao(this)).start();
    }

    public ServerSocket getServidor() {
        return servidor;
    }

    @Override
    public Socket esperandoConexao() throws IOException
    {
        return this.servidor.accept();
    }
    
    public void fecharConexao( int pos ) throws IOException
    {
        this.usuarios.get(pos).fecharConexao();
        this.usuarios.remove(pos);
    }

    public void enviarMensagem(int pos, Mensagem msg) throws IOException
    {
        this.usuarios.get(pos).enviarMensagem(msg);
    }

    public void enviarBroadCast(Mensagem msg) throws IOException
    {
        for(Cliente c: usuarios)
            c.enviarMensagem(msg);
    }

    @Override
    public void addCliente(Cliente cliente) {
        try {
            cliente.setId(gerarCodigo(MAXCHAR));
            cliente.enviarMensagem(new Mensagem(cliente.getId(), "ID"));
            cliente.iniciarEspera(this);
            this.usuarios.add(cliente);
            System.out.println("Foi adicionado um novo cliente "+cliente.getCliente().getInetAddress().getHostAddress());
        } catch (IOException e) {
            System.out.println("Nao foi possivel adicionar um novo cliente");
        }
        
    }


    @Override
    public void receber(Mensagem msg) {
        try {
            System.out.println("Mensagem recebida - "+msg);
            enviarBroadCast(msg);
            System.out.println("Broadcast Bem Sucedido");
        } catch (IOException e) {
            System.out.println("Houve algum erro ao enviar o broadcast");
        }
    }

    private static String gerarCodigo(int n)
    {
        String alfnum = "abcdefghijklmnopqrstuvwxyz0123456789";
        String cod="";
        for(int i=0 ; i < n ; i++){
            int ind = (int)(Math.random()*(alfnum.length()));
            boolean minus = Math.random() < 0.5;
            cod+= minus ? alfnum.toLowerCase().charAt(ind) : alfnum.toUpperCase().charAt(ind);
        }
        return cod;
    }

    @Override
    public void redimensionar(int addAltura, int addLargura) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'redimensionar'");
    }

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando Servidor");
            ServidorPrincipal serv = new ServidorPrincipal();
            serv.iniciarServidor();
            System.out.println("Servidor Rodando - Porta: "+ServidorPrincipal.PORTA);
        } catch (IOException e) {
            System.out.println("Nao foi possivel iniciar o servidor");
        }
    }
}
