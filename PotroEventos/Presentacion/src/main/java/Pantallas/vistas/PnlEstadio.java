package Pantallas.vistas;

import Controlador.interfaz.ICoordinadorAplicacion;
import Observer.IAsientosSeleccionadosListener;
import dtos.*;
import dtos.ENUMS.EstadoAsientoDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/**
 * Panel personalizado que representa gráficamente un estadio. Permite
 * visualizar la ocupación de asientos por secciones, seleccionar MÚLTIPLES
 * asientos, realizar zoom y notificar la selección al componente padre.
 */
public class PnlEstadio extends JPanel {

    private final Map<SeccionDTO, List<AsientoEventoDTO>> mapaOcupacion;
    //private final List<AsientoDTO> catalogoAsientos;
    private final IAsientosSeleccionadosListener listenerSeleccion;

    /**
     * Lista que guarda todos los asientos que el usuario ha seleccionado
     */
    private final List<AsientoEventoDTO> asientosSeleccionados;

    private double escala = 1.0;
    private final double ESCALA_MINIMA = 0.5;
    private final double ESCALA_MAXIMA = 3.0;
    private final int MAX_ASIENTOS = 1;
    private final ICoordinadorAplicacion coodinador;

    /**
     * Desplazamiento horizontal aplicado al panel para permitir mover
     * manualmente la vista del estadio mediante arrastre.
     */
    private int offsetX = 0;

    /**
     * Desplazamiento vertical aplicado al panel para permitir mover manualmente
     * la vista del estadio mediante arrastre.
     */
    private int offsetY = 0;

    /**
     * Última posición horizontal registrada del mouse durante el arrastre.
     */
    private int ultimoMouseX;

    /**
     * Última posición vertical registrada del mouse durante el arrastre.
     */
    private int ultimoMouseY;

    /**
     * Indica si el usuario está arrastrando el estadio.
     *
     * Si es false, se interpreta como click normal sobre un asiento. Si es
     * true, se interpreta como movimiento de la vista.
     */
    private boolean arrastrando = false;

    /**
     * Constructor del panel de estadio.
     *
     * @param mapa Mapa de ocupación.
     * @param catalogo Catálogo técnico de asientos.
     * @param listener El escuchador de eventos de selección.
     */
    public PnlEstadio(Map<SeccionDTO, List<AsientoEventoDTO>> mapa, List<AsientoDTO> catalogo, IAsientosSeleccionadosListener listener, ICoordinadorAplicacion coordinador) {
        this.mapaOcupacion = mapa;
        //this.catalogoAsientos = catalogo;
        this.listenerSeleccion = listener;
        this.asientosSeleccionados = new ArrayList<>();
        this.coodinador = coordinador;

        this.setBackground(new Color(0x1e1f20));
        this.setPreferredSize(new Dimension(900, 700));

        // REEMPLAZAR TODO el MouseAdapter actual por este
        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                ultimoMouseX = e.getX();
                ultimoMouseY = e.getY();
                arrastrando = false;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - ultimoMouseX;
                int dy = e.getY() - ultimoMouseY;

                offsetX += dx;
                offsetY += dy;

                ultimoMouseX = e.getX();
                ultimoMouseY = e.getY();

                arrastrando = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!arrastrando) {
                    detectarClick(
                            (int) ((e.getX() - offsetX) / escala),
                            (int) ((e.getY() - offsetY) / escala)
                    );
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    escala = Math.min(ESCALA_MAXIMA, escala + 0.1);
                } else {
                    escala = Math.max(ESCALA_MINIMA, escala - 0.1);
                }

                repaint();
            }
        };

        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.addMouseWheelListener(mouseHandler);
    }

    /**
     * Vacía la lista de asientos seleccionados y actualiza la vista. Útil
     * cuando el temporizador expira.
     */
    public void limpiarSeleccion() {
        this.asientosSeleccionados.clear();
        repaint();
        notificarCambioSeleccion();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(offsetX, offsetY);
        g2.scale(escala, escala);

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        g2.setColor(new Color(0x2d5a27));
        g2.fillRoundRect(centroX - 70, centroY - 45, 140, 90, 15, 15);
        g2.setColor(new Color(0x4e8a46));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(centroX - 70, centroY - 45, 140, 90, 15, 15);

        if (mapaOcupacion == null || mapaOcupacion.isEmpty()) {
            return;
        }

        List<SeccionDTO> secciones = new ArrayList<>(mapaOcupacion.keySet());
        double anguloPaso = 2 * Math.PI / secciones.size();
        int radio = 180;

        for (int i = 0; i < secciones.size(); i++) {
            int x = centroX + (int) (Math.cos(i * anguloPaso) * radio) - 100;
            int y = centroY + (int) (Math.sin(i * anguloPaso) * radio) - 50;

            dibujarBloque(g2, x, y, secciones.get(i).getNombre(), mapaOcupacion.get(secciones.get(i)));
        }
    }

    private void dibujarBloque(Graphics2D g2, int x, int y, String nombre, List<AsientoEventoDTO> asientos) {
        int size = 18, esp = 4, cols = 10;

        g2.setColor(Color.GRAY);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString(nombre, x, y - 5);

        for (int i = 0; i < asientos.size(); i++) {
            AsientoEventoDTO ae = asientos.get(i);
            AsientoDTO info = ae.getAsiento();

            int px = x + (i % cols) * (size + esp);
            int py = y + (i / cols) * (size + esp);

            if (info != null) {
                if (asientosSeleccionados.contains(ae)) {
                    g2.setColor(new Color(0x32FF6A)); // Verde: Seleccionado
                } else if (ae.getEstadoAsiento() == EstadoAsientoDTO.VENDIDO) {
                    g2.setColor(new Color(60, 60, 60)); // Gris: Vendido
                } else {
                    g2.setColor(new Color(0x1F5CCC)); // Azul: Disponible
                }

                g2.fillRoundRect(px, py, size, size, 4, 4);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 8));
                g2.drawString(info.getFila() + info.getNumero(), px + 1, py + 12);
            }
        }
    }

    private void detectarClick(int mX, int mY) {
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int size = 18, esp = 4, cols = 10, radio = 180;

        List<SeccionDTO> secciones = new ArrayList<>(mapaOcupacion.keySet());
        double anguloPaso = 2 * Math.PI / secciones.size();

        for (int i = 0; i < secciones.size(); i++) {
            int x = centroX + (int) (Math.cos(i * anguloPaso) * radio) - 100;
            int y = centroY + (int) (Math.sin(i * anguloPaso) * radio) - 50;

            SeccionDTO seccionActual = secciones.get(i);
            List<AsientoEventoDTO> asientos = mapaOcupacion.get(seccionActual);

            for (int j = 0; j < asientos.size(); j++) {
                int px = x + (j % cols) * (size + esp);
                int py = y + (j / cols) * (size + esp);

                if (mX >= px && mX <= px + size && mY >= py && mY <= py + size) {
                    AsientoEventoDTO ae = asientos.get(j);

                    // Buscar si ya está seleccionado por ID
                    AsientoEventoDTO seleccionado = null;

                    for (AsientoEventoDTO item : asientosSeleccionados) {
                        if (item.getAsiento().getIdAsiento().equals(ae.getAsiento().getIdAsiento())) {
                            seleccionado = item;
                            break;
                        }
                    }

                    // Si ya estaba seleccionado → quitar selección
                    if (seleccionado != null) {
                        boolean liberado = coodinador.liberarAsiento(
                                ae.getIdAsientoEvento()
                        );

                        if (liberado) {
                            asientosSeleccionados.remove(seleccionado);
                            ae.setEstadoAsiento(EstadoAsientoDTO.DISPONIBLE);
                        }

                        repaint();
                        notificarCambioSeleccion();
                        return;
                    }

                    // Si ya fue vendido → no permitir
                    if (ae.getEstadoAsiento() == EstadoAsientoDTO.VENDIDO) {
                        return;
                    }

                    // Límite máximo
                    if (asientosSeleccionados.size() >= MAX_ASIENTOS) {
                        return;
                    }

                    // Intentar reservar
                    boolean reservado = coodinador.reservarAsiento(
                            ae.getIdAsientoEvento()
                    );

                    if (reservado) {
                        ae.setEstadoAsiento(EstadoAsientoDTO.RESERVADO);
                        asientosSeleccionados.add(ae);
                    }

                    repaint();
                    notificarCambioSeleccion();
                    return;
                }
            }
        }
    }

    /**
     * Reúne toda la información de los asientos seleccionados y notifica a la
     * vista.
     */
    private void notificarCambioSeleccion() {
        if (listenerSeleccion == null) {
            return;
        }

        List<SeccionDTO> listSecciones = new ArrayList<>();
        List<AsientoDTO> listInfo = new ArrayList<>();

        for (AsientoEventoDTO ae : asientosSeleccionados) {
            listInfo.add(ae.getAsiento());
            listSecciones.add(ae.getAsiento().getSeccion());
        }

        listenerSeleccion.onSeleccionCambiada(listSecciones, listInfo, asientosSeleccionados);
    }

}
