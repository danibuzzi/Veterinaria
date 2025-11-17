package com.veterinaria.modelo;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de tabla para la consulta de turnos por propietario.
 * Columnas: [ID, Fecha, Hora, Tipo Consulta, Mascota, Estado]
 * La columna ID y Estado serán ocultadas en la vista.
 */
public class TurnoTableModelConsulta extends AbstractTableModel {

    // Los datos son una lista de arrays de objetos, donde cada array es una fila
    private List<Object[]> datos;
    // CRÍTICO: Se añade "ID" (índice 0) y "Estado" (índice 5)
    private final String[] columnas = {"ID", "Fecha", "Hora", "Tipo Consulta", "Mascota", "Estado"};

    public TurnoTableModelConsulta() {
        // Inicializa la lista vacía
        this.datos = new ArrayList<>();
    }

    /**
     * Establece los datos de la tabla y notifica a la vista para que se actualice.
     * @param datos Lista de Object[] con los datos.
     */
    public void setDatos(List<Object[]> datos) {
        this.datos = datos;
        // Notifica a la tabla que los datos han cambiado
        fireTableDataChanged();
    }

    // Método para obtener el ID de un turno seleccionado (útil internamente)
    public Integer getIdTurnoAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < datos.size()) {
            // El ID está en el índice 0 de la fila Object[]
            Object id = datos.get(rowIndex)[0];
            if (id instanceof Integer) {
                return (Integer) id;
            }
        }
        return null;
    }


    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // Se valida el índice para evitar IndexOutOfBoundsException si el modelo de columnas
        // es accedido antes de que la vista lo tenga totalmente cargado (aunque AbstractTableModel
        // se encarga de esto)
        if (columnIndex >= 0 && columnIndex < columnas.length) {
            return columnas[columnIndex];
        }
        return null; // O una cadena vacía, dependiendo de la política
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < datos.size() && columnIndex >= 0 && columnIndex < columnas.length) {
            // Devolvemos el valor de la celda
            return datos.get(rowIndex)[columnIndex];
        }
        return null; // Devuelve null si está fuera de los límites de los datos.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Ninguna celda es editable en una vista de consulta
        return false;
    }
}