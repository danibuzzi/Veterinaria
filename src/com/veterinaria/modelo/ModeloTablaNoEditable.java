package com.veterinaria.modelo;



import javax.swing.table.DefaultTableModel;

/**
 * Modelo de tabla personalizado que garantiza que todas las celdas
 * de la JTable sean NO EDITABLES por el usuario.
 */
public class ModeloTablaNoEditable extends DefaultTableModel {

    // Constructor que acepta los datos (Object[][]) y las cabeceras (String[])
    public ModeloTablaNoEditable(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    /**
     * Sobreescribe el método isCellEditable para devolver siempre 'false'.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
