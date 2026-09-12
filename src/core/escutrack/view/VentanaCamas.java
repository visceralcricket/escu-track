package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import core.escutrack.utils.ValidadorCamposUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCamas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public VentanaCamas(VentanaPrincipal ventana, ControladorHospital controlador) {
		setLayout(null);
		
		JButton btnRegistrarCama = new JButton("1. Agregar Cama a Departamento");
		btnRegistrarCama.setBounds(100, 50, 250, 40); 
		btnRegistrarCama.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento destino:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID de la nueva Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	String prioridad = javax.swing.JOptionPane.showInputDialog(ventana, "Prioridad (entero):");
	            	if(prioridad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarPrioridadCama(prioridad);
	            	int prioridadEntera = Integer.parseInt(prioridad);
	            	
	            	controlador.agregarCama(depto, cama, prioridadEntera);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Cama registrada con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnRegistrarCama);
	    
	    JButton btnMostrarCamas = new JButton("2. Mostrar Camas de Departamento particular");
	    btnMostrarCamas.setBounds(100, 100, 250, 40); 
	    btnMostrarCamas.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	        		
	            	String camasEncontradas = controlador.mostrarCamasPorDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, camasEncontradas);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnMostrarCamas);
	    
	    JButton btnEliminarCama = new JButton("3. Eliminar Cama");
	    btnEliminarCama.setBounds(100, 150, 250, 40); 
	    btnEliminarCama.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento de la cama a eliminar:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID de la Cama a eliminar:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	controlador.eliminarCama(depto, cama);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Cama eliminada con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnEliminarCama);
	    
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
