package com.veterinaria.modelo;

import com.veterinaria.modelo.Mascota;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





    public class MascotaTableModel extends AbstractTableModel {

        // Usamos 'ofPattern' de DateTimeFormatter, específico para java.time
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        private List<Mascota> data = new ArrayList<>();

        // Columnas internas: 0.ID (Oculto), 1.Nombre, 2.F. Nacimiento, 3.Especie, 4.Raza, 5.Sexo
        private final String[] columnNames = {"ID", "Nombre", "F. Nacimiento", "Especie", "Raza", "Sexo"};

        public void setData(List<Mascota> data) {
            this.data = data;
            fireTableDataChanged(); // Notifica a la vista que los datos cambiaron
        }

        // Método para obtener la mascota seleccionada (usando el índice de la fila)
        public Mascota getMascotaAt(int rowIndex) {
            if (rowIndex >= 0 && rowIndex < data.size()) {
                return data.get(rowIndex);
            }
            return null;
        }

        @Override
        public int getRowCount() { return data.size(); }

        @Override
        public int getColumnCount() { return columnNames.length; } // Son 6 columnas internas

        @Override
        // Devolvemos el nombre de la columna para la cabecera de la tabla
        public String getColumnName(int column) {
            // Aunque la columna 0 es 'ID', la ocultaremos en la vista.
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Mascota mascota = data.get(rowIndex);
            switch (columnIndex) {
                case 0: return mascota.getIdMascota(); // ID (OCULTO)
                case 1: return mascota.getNombre();
                case 2:
                   LocalDate fechaNac = mascota.getFechaNacimiento();
                    if (fechaNac != null) {
                        return fechaNac.format(DATE_FORMATTER); // Aplica el formato "dd/MM/yyyy"
                    }
                    return "";
                    //return mascota.getFechaNacimiento() != null ? mascota.getFechaNacimiento().toString() : "";
                case 3: return mascota.getEspecie();
                case 4: return mascota.getRaza(); // <--- CRÍTICO: Se incluye Raza
                case 5: return mascota.getSexo();
                default: return null;
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) { return Integer.class; }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

