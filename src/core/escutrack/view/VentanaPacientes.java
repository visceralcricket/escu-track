package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import core.escutrack.utils.ValidadorCamposUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPacientes extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public VentanaPacientes(VentanaPrincipal ventana, ControladorHospital controlador) {
		setLayout(null);
		
		JButton btnRegistrar = new JButton("1. Registrar nuevo Paciente");
	    // Al usar Absolute Layout, el botón usa coordenadas y tamaño exactos (x, y, ancho, alto)
	    btnRegistrar.setBounds(100, 50, 250, 40); 
	    btnRegistrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        	
	            	String rut = javax.swing.JOptionPane.showInputDialog(ventana, "RUT:");
	            	if(rut == null) return; // Si el usuario presiona 'cancelar'
	            	core.escutrack.utils.ValidadorCamposUtils.validarRut(rut);
	            	
	            	String nombre = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre:");
	            	if(nombre == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarNombre(nombre);
	            	
	            	String gravedad = javax.swing.JOptionPane.showInputDialog(ventana, "Gravedad (ej. estable):");
	            	if(gravedad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarGravedad(gravedad);
	            	
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Paciente registrado exitosamente.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnRegistrar);
	    
	    JButton btnMostrarPaciente = new JButton("2. Mostrar Paciente específico");
	    btnMostrarPaciente.setBounds(100, 100, 250, 40); 
	    btnMostrarPaciente.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	String pacienteEncontrado = controlador.mostrarPaciente(cama, depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "--- BÚSQUEDA DE PACIENTE --- \n\n" + pacienteEncontrado);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnMostrarPaciente);
	    
	    JButton btnFiltrarGravedad = new JButton("3. Filtrar Pacientes por gravedad");
	    btnFiltrarGravedad.setBounds(100, 150, 250, 40); 
	    btnFiltrarGravedad.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String gravedad = javax.swing.JOptionPane.showInputDialog(ventana, "Ingrese gravedad a filtrar:");
	            	if(gravedad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarGravedad(gravedad);
	            	
	            	String pacientes = controlador.filtrarPorGravedad(gravedad);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "--- PACIENTE ENCONTRADOS ---\n\n" + pacientes);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnFiltrarGravedad);
	    
	    JButton btnVolver = new JButton("Regresar al menú principal");
	    btnVolver.setBounds(100, 210, 250, 40);
	    btnVolver.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		ventana.mostrarMenuPrincipal();
	    	}
	    });
	    add(btnVolver);
	}   
}
