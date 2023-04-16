import java.io.IOException;

import javax.swing.JOptionPane;

public class Escrita implements Runnable{
    private Cliente cliente;
    private Mensagem msg;
    public Escrita(Cliente cliente, Mensagem msg)
    {
        this.cliente = cliente;
        this.msg = msg;
    }
    @Override
    public void run() {
        try {
            cliente.enviarMensagem(msg);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não conseguiu adicionar a mensagem no buffer.");
        }
    }
    
}
