import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class EnvioGUI extends JFrame{
    
    private SeccaoMensagem secmsg;
    private JTextField caixamsg;
    private Cliente backup;

    public EnvioGUI()
    {
        super("Conectando...");
        gerarConexoes();
        secmsg = new SeccaoMensagem( this.backup );
        caixamsg = new JTextField();

        //Instanciaçao dos elementos
        JPanel painelInferior = new JPanel();
        JButton botaoEnviar = new JButton();
        JScrollPane jsp = new JScrollPane(secmsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        botaoEnviar.setSize(60, 60);
        botaoEnviar.setIcon(new ImageIcon("recursos/envicon.png"));
        botaoEnviar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validarCampo())
                {
                    enviarMensagem(caixamsg.getText());
                    caixamsg.setText("");
                }
            }
        });


        painelInferior.setLayout(new BorderLayout(0,1));
        painelInferior.add(caixamsg, BorderLayout.CENTER);
        painelInferior.add(botaoEnviar, BorderLayout.EAST);

        //Adicionar elementos a tela principal
        setLayout(new BorderLayout(0, 1));
        add(jsp, BorderLayout.CENTER);
        add(painelInferior,  BorderLayout.SOUTH);

        //Definicao da tela principal
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void gerarConexoes()
    {
        try{
            backup = new Cliente();
            Mensagem m = backup.esperandoMensagem();
            backup.setId(m.getIdEmissor());
            this.setTitle(backup.getId());
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Nao conseguiu se conectar ao servidor");
            System.exit(0);
        }
    }

    private boolean validarCampo()
    {
        if(!caixamsg.getText().isEmpty())
            return true;
        JOptionPane.showMessageDialog(this, "O campo está vazio, Chefe");
        return false;
    }

    private void enviarMensagem(String msg)
    {
        Escrita escrita = new Escrita(backup, new Mensagem(backup.getId(), msg));
        EscritaThread escThread = new EscritaThread(escrita);
        escThread.start();
    }
}
