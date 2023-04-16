import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextArea;

public class SeccaoMensagem extends JTextArea implements  Actualizacao{
    private Registro registro;
    private Cliente cliente;
    
    public SeccaoMensagem(Cliente cliente)
    {
        super();
        registro = new Registro();
        this.cliente = cliente;
        cliente.iniciarEspera(this);
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("Desenhando");
        setBackground(new Color(0xFF1E1E1E));
        Graphics2D g2d = (Graphics2D) g;
        this.registro.desenhar(g2d,this.getBounds().width,this.getBounds().height, this.cliente.getId(), this);
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public void redimensionar(int addAltura, int addLargura) {
        setPreferredSize(new Dimension(getWidth()+addLargura,getHeight()+addAltura));
        setText("escrevendo");
    }

    @Override
    public void receber(Mensagem msg) {
        registro.adicionar(msg);
        this.repaint();
    }
}
