public class TelaGUI{
	final private static int QUANTIDADE_TELA = 1;
    public static void main(String[] args)
    {
        mostrar(QUANTIDADE_TELA);
    }
    private static void mostrar(int n)
    {
        for( int i = 0 ; i<n ; i++ )
        {
            EnvioGUI tela = new EnvioGUI();
            tela.setSize(400,600);
            tela.setVisible(true);
        }
    }
    
}
