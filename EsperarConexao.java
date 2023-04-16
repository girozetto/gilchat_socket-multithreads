import java.io.IOException;
import java.net.Socket;

public class EsperarConexao implements Runnable{

    private EsperaServ ligacaoServidor;
    private boolean interruptor;
    public EsperarConexao(EsperaServ ligacaoServidor) {
        this.ligacaoServidor = ligacaoServidor;
        this.interruptor = true;
    }

    @Override
    public void run() {
        while(this.interruptor){
            try {
                Socket sockt = this.ligacaoServidor.esperandoConexao();
                this.ligacaoServidor.addCliente(new Cliente(sockt));
            } catch (IOException e) {
                System.out.println("Nao conseguiu obter o cliente!!! "+e);
            }
        }
    }
    
}
