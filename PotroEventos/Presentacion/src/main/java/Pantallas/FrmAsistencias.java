package Pantallas;

import Controlador.interfaz.ICoordinadorAplicacion;
import dtos.AsientoDTO;
import dtos.AsientoEventoDTO;
import dtos.ENUMS.EstadoAsientoDTO;
import dtos.EventoDTO;
import dtos.ReporteAsistenciaDTO;
import dtos.SeccionDTO;
import excepciones.CoordinadorException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JScrollPane;

/**
 * Pantalla para el registro y visualización gráfica de asistencias.
 *
 * * @author Brian Sandoval - 262741
 */
public class FrmAsistencias extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmAsistencias.class.getName());

    private final EventoDTO evento;
    private final ICoordinadorAplicacion coordinador;
    private Map<SeccionDTO, List<AsientoEventoDTO>> mapaOcupacion;
    private List<AsientoEventoDTO> asientosAsistidos;

    /**
     * Nuevo constructor adaptado para recibir el evento y el coordinador.
     */
    public FrmAsistencias(EventoDTO evento, ICoordinadorAplicacion coordinador) {
        this.evento = evento;
        this.coordinador = coordinador;

        initComponents();
        cargarDatosYAsientos();
    }

    private void cargarDatosYAsientos() {
        try {
            lblTitulo.setText("Registro Asistencias: " + evento.getNombreEvento());

            this.mapaOcupacion = coordinador.obtenerMapaOcupacion(evento);
            this.asientosAsistidos = coordinador.obtenerAsientosConAsistencia(evento.getIdEvento());

            if (asientosAsistidos == null) {
                asientosAsistidos = new ArrayList<>();
            }

            PnlAsientos.setLayout(new BorderLayout());
            PnlDibujoAsistencias visor = new PnlDibujoAsistencias(mapaOcupacion, asientosAsistidos);
            JScrollPane scrollPane = new JScrollPane(visor);
            scrollPane.setBorder(null);
            PnlAsientos.add(scrollPane, BorderLayout.CENTER);
            PnlAsientos.revalidate();
            PnlAsientos.repaint();

            cargarEstadisticas();

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al inicializar el panel de asistencias", e);
            JOptionPane.showMessageDialog(this, "Error al cargar el mapa de asistencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Componente gráfico interno que dibuja los asientos de corrido sin
     * importar la sección.
     */
    private static class PnlDibujoAsistencias extends JPanel {

        private final List<AsientoEventoDTO> todosLosAsientos;
        private final List<AsientoEventoDTO> asistidos;
        private final int columnas = 12;   // Cuántos asientos por fila en pantalla
        private final int sizeSquare = 22; // Tamaño del cuadro del asiento
        private final int espacio = 5;     // Espaciado entre asientos

        public PnlDibujoAsistencias(Map<SeccionDTO, List<AsientoEventoDTO>> mapa, List<AsientoEventoDTO> asistidos) {
            this.asistidos = asistidos;
            this.setBackground(new Color(0x1e1f20));
            this.todosLosAsientos = new ArrayList<>();

            // 1. Aplanar el mapa para meter absolutamente todos los asientos en una sola lista continua
            if (mapa != null) {
                for (List<AsientoEventoDTO> listaAsientos : mapa.values()) {
                    if (listaAsientos != null) {
                        todosLosAsientos.addAll(listaAsientos);
                    }
                }
            }

            // 2. Calcular la altura dinámica basada en el total general de asientos
            int totalAsientos = todosLosAsientos.size();
            int filasSillas = (int) Math.ceil((double) totalAsientos / columnas);
            int totalHeight = 40 + (filasSillas * (sizeSquare + espacio)) + 40;

            this.setPreferredSize(new Dimension(800, Math.max(500, totalHeight)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (todosLosAsientos == null || todosLosAsientos.isEmpty()) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                g2.drawString("No hay asientos registrados para este evento.", 30, 40);
                return;
            }

            int startX = 30; // Margen izquierdo
            int startY = 30; // Margen superior

            // 3. Pintar los cuadritos uno tras otro de forma secuencial
            for (int i = 0; i < todosLosAsientos.size(); i++) {
                AsientoEventoDTO ae = todosLosAsientos.get(i);
                AsientoDTO info = ae.getAsiento();

                if (info == null) {
                    continue;
                }

                // Calcular posiciones X y Y exactas en la cuadrícula de 12 columnas
                int px = startX + (i % columnas) * (sizeSquare + espacio);
                int py = startY + (i / columnas) * (sizeSquare + espacio);

                // Validar si el asiento actual ya asistió
                boolean yaAsistio = false;
                if (asistidos != null) {
                    for (AsientoEventoDTO asistido : asistidos) {
                        if (asistido.getIdAsientoEvento().equals(ae.getIdAsientoEvento())) {
                            yaAsistio = true;
                            break;
                        }
                    }
                }

                // Definición de colores según el estado
                if (yaAsistio) {
                    g2.setColor(new Color(0x32FF6A)); // VERDE: Confirmado
                } else if (ae.getEstadoAsiento() == EstadoAsientoDTO.VENDIDO) {
                    g2.setColor(new Color(90, 90, 90)); // GRIS: Vendido, no ha venido
                } else {
                    g2.setColor(new Color(45, 45, 45)); // GRIS OSCURO: Libre/No vendido
                }

                // Dibujar el cuadro del asiento
                g2.fillRoundRect(px, py, sizeSquare, sizeSquare, 5, 5);

                // Borde sutil
                g2.setColor(new Color(0x333333));
                g2.drawRoundRect(px, py, sizeSquare, sizeSquare, 5, 5);

                // Dibujar texto (Fila + Número) centrado
                g2.setColor(yaAsistio ? Color.BLACK : Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 9));
                String labelAsiento = info.getFila() + info.getNumero();

                FontMetrics fm = g2.getFontMetrics();
                int tx = px + (sizeSquare - fm.stringWidth(labelAsiento)) / 2;
                int ty = py + ((sizeSquare - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(labelAsiento, tx, ty);
            }
        }
    }

    public void cargarEstadisticas() {
        try {
            // 2. Estadísticas directo desde el conteo del backend
            ReporteAsistenciaDTO reporte = coordinador.obtenerAsistencias(evento.getIdEvento());
            long confirmados = reporte.getAsistidos();
            long pendientes = reporte.getPendientes();

            lblConfirmados.setText(String.valueOf(confirmados));
            lblRestantes.setText(String.valueOf(pendientes));

        } catch (CoordinadorException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void refrescarAsistencias() {
        try {
            this.asientosAsistidos = coordinador.obtenerAsientosConAsistencia(evento.getIdEvento());
            if (asientosAsistidos == null) {
                asientosAsistidos = new ArrayList<>();
            }

            PnlAsientos.removeAll();
            PnlAsientos.setLayout(new BorderLayout());
            PnlDibujoAsistencias visor = new PnlDibujoAsistencias(mapaOcupacion, asientosAsistidos);
            JScrollPane scrollPane = new JScrollPane(visor);
            scrollPane.setBorder(null);
            PnlAsientos.add(scrollPane, BorderLayout.CENTER);
            PnlAsientos.revalidate();
            PnlAsientos.repaint();

            cargarEstadisticas();

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al refrescar asistencias", e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        PnlAsientos = new javax.swing.JPanel();
        PnlAsistencias = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblConfirmados = new javax.swing.JLabel();
        lblRestantes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        jPanel3.setBackground(new java.awt.Color(0, 49, 141));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(31, 92, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PotroEventos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnVolver.setBackground(new java.awt.Color(31, 92, 204));
        btnVolver.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.setBorderPainted(false);
        btnVolver.setOpaque(true);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 0, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Registro Asistencias");

        javax.swing.GroupLayout PnlAsientosLayout = new javax.swing.GroupLayout(PnlAsientos);
        PnlAsientos.setLayout(PnlAsientosLayout);
        PnlAsientosLayout.setHorizontalGroup(
            PnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 855, Short.MAX_VALUE)
        );
        PnlAsientosLayout.setVerticalGroup(
            PnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PnlAsistencias.setBackground(new java.awt.Color(243, 243, 243));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asistencias");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Confirmados");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Restantes");

        lblConfirmados.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblConfirmados.setForeground(new java.awt.Color(31, 92, 204));
        lblConfirmados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfirmados.setText("1");

        lblRestantes.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblRestantes.setForeground(new java.awt.Color(233, 134, 20));
        lblRestantes.setText("25");

        javax.swing.GroupLayout PnlAsistenciasLayout = new javax.swing.GroupLayout(PnlAsistencias);
        PnlAsistencias.setLayout(PnlAsistenciasLayout);
        PnlAsistenciasLayout.setHorizontalGroup(
            PnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PnlAsistenciasLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(PnlAsistenciasLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(lblConfirmados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRestantes)
                .addGap(70, 70, 70))
        );
        PnlAsistenciasLayout.setVerticalGroup(
            PnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlAsistenciasLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblConfirmados)
                    .addGroup(PnlAsistenciasLayout.createSequentialGroup()
                        .addComponent(lblRestantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(0, 362, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PnlAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PnlAsistencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PnlAsistencias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PnlAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        try {
            coordinador.mostrarConsultarEventos();
        } catch (CoordinadorException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlAsientos;
    private javax.swing.JPanel PnlAsistencias;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblConfirmados;
    private javax.swing.JLabel lblRestantes;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
