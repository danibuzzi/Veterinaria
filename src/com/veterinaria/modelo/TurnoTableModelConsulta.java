package com.veterinaria.modelo;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de tabla para la consulta de turnos por propietario.
 * Usa List<Object[]> directamente (sin DTO), para pasar los datos a la JTable.
 * Columnas: [Fecha, Hora, Tipo Consulta, Mascota]
 */
public class TurnoTableModelConsulta extends AbstractTableModel {

    // Los datos son una lista de arrays de objetos, donde cada array es una fila
    private List<Object[]> datos;
    private final String[] columnas = {"Fecha", "Hora", "Tipo Consulta", "Mascota"};

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
        return columnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < datos.size() && columnIndex >= 0 && columnIndex < columnas.length) {
            // Devolvemos el valor de la fila (array) en la columna (índice) solicitada.
            return datos.get(rowIndex)[columnIndex];
        }
        return null;
    }
}