import java.io.IOException;
import java.net.Socket;

public interface EsperaServ {
    void addCliente(Cliente cliente);
    Socket esperandoConexao() throws IOException;
}
