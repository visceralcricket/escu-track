package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import core.escutrack.exceptions.EntidadNoEncontradaException;
import core.escutrack.model.Cama;
import core.escutrack.utils.ValidadorCamposUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;

public class VentanaDepartamentos extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public VentanaDepartamentos(VentanaPrincipal ventana, ControladorHospital controlador) {
		setLayout(null);
		
		JButton btnRegistrarDpto = new JButton("1. Agregar Departamento");
		btnRegistrarDpto.setBounds(100, 50, 250, 40); 
		btnRegistrarDpto.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del nuevo departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	controlador.agregarDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Departamento creado con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnRegistrarDpto);
	    
	    JButton btnMostrarDepartamentos = new JButton("2. Mostrar Departamentos");
	    btnMostrarDepartamentos.setBounds(100, 100, 250, 40); 
	    btnMostrarDepartamentos.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String departamentosEncontrados = controlador.mostrarDepartamentos();
	                javax.swing.JOptionPane.showMessageDialog(ventana, departamentosEncontrados);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnMostrarDepartamentos);
	    
	    JButton btnEliminarDepartamento = new JButton("3. Eliminar Departamento");
	    btnEliminarDepartamento.setBounds(100, 150, 250, 40); 
	    btnEliminarDepartamento.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del departamento a eliminar:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	controlador.eliminarDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Departamento eliminado con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnEliminarDepartamento);
	    
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
