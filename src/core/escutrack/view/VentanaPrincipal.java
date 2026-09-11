package core.escutrack.view;

// Necesario para importar funciones del controlador a los botones
import core.escutrack.controller.ControladorHospital;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Módulo de vista (patrón MVC) que implementa el Modo Ventana del programa
 * mediante Swing: una interfaz gráfica construida con WindowBuilder que
 * delega toda la lógica de negocio al {@link ControladorHospital} recibido
 * por constructor.
 * <p>
 * Actualmente solo incluye los botones esenciales de registro de paciente y
 * guardado/salida (ver TODO al final de la clase); el resto de operaciones
 * disponibles en el Modo Consola aún no tienen equivalente gráfico.
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ControladorHospital controlador;

    /**
     * Construye y arma la ventana principal del Modo Ventana, incluyendo
     * sus botones y el comportamiento asociado a cada uno.
     *
     * @param controlador controlador con la lógica de negocio del hospital, usado por los botones de la ventana
     */
    // Único constructor que exige el controlador y arma la ventana
    public VentanaPrincipal(ControladorHospital controlador) {

        this.controlador = controlador;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Absolute Layout NECESARIO que permite mover cosas libremente
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        mostrarMenuPrincipal();
    }
    
    public void cambiarPanel(JPanel nuevoPanel) {
    	setContentPane(nuevoPanel);
    	revalidate();
    	repaint();
    }
    
    public void mostrarMenuPrincipal() {
    	contentPane.removeAll();
    	JButton btnMenuDepartamentos = new JButton("1. Gestión de Departamentos");
    	btnMenuDepartamentos.setBounds(100,50,250,40);
    	btnMenuDepartamentos.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			VentanaDepartamentos submenu = new VentanaDepartamentos(VentanaPrincipal.this, controlador);
    			cambiarPanel(submenu);
    		}
    	});
    	contentPane.add(btnMenuDepartamentos);
    	
    	JButton btnMenuCamas = new JButton("2. Gestión de Camas");
    	btnMenuCamas.setBounds(100,100,250,40);
    	btnMenuCamas.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			VentanaCamas submenu = new VentanaCamas(VentanaPrincipal.this, controlador);
    			cambiarPanel(submenu);
    		}
    	});
    	contentPane.add(btnMenuCamas);
    	
    	JButton btnMenuPacientes = new JButton("3. Gestión de Pacientes");
    	btnMenuPacientes.setBounds(100,150,250,40);
    	btnMenuPacientes.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			VentanaPacientes submenu = new VentanaPacientes(VentanaPrincipal.this, controlador);
    			cambiarPanel(submenu);
    		}
    	});
    	contentPane.add(btnMenuPacientes);
    	
    	JButton btnSalir = new JButton("4. Guardar y Salir");
        btnSalir.setBounds(100, 210, 250, 40);
        // Al presionar el botón: persiste el estado del hospital y cierra el programa.
        btnSalir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
        			controlador.apagarSistema();
        			System.exit(0);
        		}
        		catch(Exception e) {
        			javax.swing.JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al guardar: " + e.getMessage());
        		}
        	}
        });
        contentPane.add(btnSalir);
        
        setContentPane(contentPane);
        contentPane.revalidate();
        contentPane.repaint();
    } 
}