package core.escutrack.view;

// Necesario para importar funciones del controlador a los botones
import core.escutrack.controller.ControladorHospital;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/* +++
 * @README
 * 
 * Se detectaron errores al intentar abrir este código fuente en modo Diseño
 * con WindowBuilder y, aparentemente, es debido a cómo el parseador del
 * plugin analiza la sintaxis de llamas a métodos anidados como 'initialize'.
 * Por esta necesidad de poder abrir la ventana en modo Diseño para continuar
 * su desarrollo, se decidió hacer uso de CardLayout para mitigar el problema.
 * 
 * @author Felipe T.S.
 * @since v0.2.3
 --- */
import java.awt.CardLayout;

/* +++
 * Módulo de vista (patrón MVC) que implementa el Modo Ventana del programa
 * mediante Swing: una interfaz gráfica construida con WindowBuilder que
 * delega toda la lógica de negocio al {@link ControladorHospital} recibido
 * por constructor.
 * 
 --- */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ControladorHospital controlador;
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    public VentanaPrincipal(ControladorHospital controlador) {
        this.controlador = controlador;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // tamaño estándar definido
        setBounds(100, 100, 512, 512);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Panel contenedor que ocupa todo el espacio (CardLayout)
        panelContenedor = new JPanel();
        panelContenedor.setBounds(0, 0, 496, 473); // ajustado a los márgenes internos de 512x512
        cardLayout = new CardLayout(0, 0);
        panelContenedor.setLayout(cardLayout);
        contentPane.add(panelContenedor);

        JPanel panelMenuPrincipal = new JPanel();
        panelMenuPrincipal.setLayout(null);
        panelContenedor.add(panelMenuPrincipal, "MenuPrincipal");

        JButton btnMenuDepartamentos = new JButton("1. Gestión de Departamentos");
        btnMenuDepartamentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                VentanaDepartamentos panelDeptos = new VentanaDepartamentos(VentanaPrincipal.this, controlador);
                panelContenedor.add(panelDeptos, "MenuDepartamentos");
                cardLayout.show(panelContenedor, "MenuDepartamentos");
            }
        });
        btnMenuDepartamentos.setBounds(123, 175, 250, 40);
        panelMenuPrincipal.add(btnMenuDepartamentos);

        JButton btnMenuCamas = new JButton("2. Gestión de Camas");
        btnMenuCamas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                VentanaCamas panelCamas = new VentanaCamas(VentanaPrincipal.this, controlador);
                panelContenedor.add(panelCamas, "MenuCamas");
                cardLayout.show(panelContenedor, "MenuCamas");
            }
        });
        btnMenuCamas.setBounds(123, 246, 250, 40);
        panelMenuPrincipal.add(btnMenuCamas);

        JButton btnMenuPacientes = new JButton("3. Gestión de Pacientes");
        btnMenuPacientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                VentanaPacientes panelPacientes = new VentanaPacientes(VentanaPrincipal.this, controlador);
                panelContenedor.add(panelPacientes, "MenuPacientes");
                cardLayout.show(panelContenedor, "MenuPacientes");
            }
        });
        btnMenuPacientes.setBounds(123, 316, 250, 40);
        panelMenuPrincipal.add(btnMenuPacientes);

        JButton btnSalir = new JButton("4. Guardar y Salir");
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.apagarSistema();
                    System.exit(0);
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al guardar: " + ex.getMessage());
                }
            }
        });
        btnSalir.setBounds(123, 386, 250, 40);
        panelMenuPrincipal.add(btnSalir);

        // Mostrar menú principal al inicio
        cardLayout.show(panelContenedor, "MenuPrincipal");
    }

    /**
     * Método público para permitir que los subpaneles regresen al menú principal
     */
    public void mostrarMenuPrincipal() {
        cardLayout.show(panelContenedor, "MenuPrincipal");
    }
}