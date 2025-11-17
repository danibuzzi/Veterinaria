package com.veterinaria.modelo;

import com.veterinaria.modelo.Propietario;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class PropietarioTableModel extends AbstractTableModel {

    private List<Propietario> datos;

    private final String[] nombresColumna = {"ID", "DNI", "Apellidos", "Nombres", "Email", "Teléfono"};

    public PropietarioTableModel(List<Propietario> datos,String[] nombresColumna) {
        this.datos = new ArrayList<>(datos);
    }

    // Método para refrescar los datos
    public void setDatos(List<Propietario> nuevosDatos) {
        if (nuevosDatos != null) {
            // Aseguramos que solo guardamos objetos no nulos
            this.datos = nuevosDatos.stream()
                    .filter(p -> p != null)
                    .collect(java.util.stream.Collectors.toList());
        } else {
            this.datos = new ArrayList<>();
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return nombresColumna.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return nombresColumna[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (datos == null || datos.isEmpty() || rowIndex < 0 || rowIndex >= datos.size()) {
            return null;
        }

        Propietario p = datos.get(rowIndex);

        if (p == null) {
            return null;
        }

        switch (columnIndex) {
            case 0: return p.getIdPropietario(); // ID (Se usará para la modificación/eliminación, generalmente se oculta en la vista)
            case 1: return p.getDni();
            case 2: return p.getApellido();
            case 3: return p.getNombre();
            case 4: return p.getEmail();
            case 5: return p.getTelefono();
            default: return null;
        }
    }

    // Necesario para que el ID se maneje como un Integer
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        }
        return String.class;
    }

    // Método para obtener el objeto Propietario de una fila (útil para el Controlador)
    /*public Propietario getPropietarioAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < datos.size()) {
            return datos.get(rowIndex);
        }
        return null;
    }*/


    public Propietario getPropietarioAt(int rowIndex) {
        if (datos == null || rowIndex < 0 || rowIndex >= datos.size()) {
            // Devuelve null si no hay datos o el índice es inválido
            return null;
        }
        // Devuelve el objeto, que podría ser null si se insertó incorrectamente
        return datos.get(rowIndex);
    }

    // Si necesitas obtener el objeto Propietario completo:
    public Propietario getPropietario(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= datos.size()) {
            return null;
        }
        return datos.get(rowIndex);
    }
}
