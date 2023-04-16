import java.io.IOException;

import javax.swing.JOptionPane;

public class Leitura implements Runnable{
    private Cliente cliente;
    private Actualizacao act;
    private boolean interruptor;
    public Leitura(Cliente cliente, Actualizacao act)
    {
        this.act=act;
        this.cliente=cliente;
        this.interruptor = true;
    }


    @Override
    public void run() {
        while(interruptor){
            try {
                Mensagem ms = this.cliente.esperandoMensagem();
                act.receber(ms);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível processar a mensagem: "+e.toString());
            }
        }
    }
}
