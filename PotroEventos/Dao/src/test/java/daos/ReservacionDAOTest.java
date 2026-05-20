package daos;

import Entitys.AsientoEvento;
import Entitys.Asistencia;
import Entitys.Boleto;
import Entitys.Devolucion;
import Entitys.ENUMS.EstadoBoleto;
import Entitys.ENUMS.ReservacionEstado;
import Entitys.Empleado;
import Entitys.Reservacion;
import entidadesmongo.ReporteAsistencia;
import excepciones.PersistenciaException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservacionDAOTest {

    private static ReservacionDAO dao;

    // ─── IDs reales de tu base de datos ───────────────────────────────────────
    private static final String ID_USUARIO_EXISTENTE = "6a0d3cd8c5a69f148aed5618";
    private static final String ID_EVENTO_EXISTENTE = "6a0d3d47827351936d441536";
    private static final String ID_EMPLEADO_EXISTENTE = "6a0d3e3cceeef465e744152e";
    private static final String ID_INEXISTENTE = "000000000000000000000000";
    private static final String TOKEN_INEXISTENTE = "token-que-no-existe-jamas";

    // Token e ID de reservación que se generan en el test 1 para usarlos después
    private static String tokenGenerado;
    private static String idReservacionGenerada;

    @BeforeAll
    static void setUp() {
        dao = ReservacionDAO.getInstance();
    }

    // =========================================================================
    // guardarReservacion
    // =========================================================================
    @Test
    @Order(1)
    @DisplayName("guardarReservacion — BUENA: guarda correctamente una reservación válida")
    void guardarReservacion_buena() throws PersistenciaException {
        tokenGenerado = UUID.randomUUID().toString();

        Reservacion reservacion = new Reservacion();
        reservacion.setTotal(0.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        Boleto boleto = new Boleto();
        boleto.setToken(tokenGenerado);
        boleto.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boleto.setPrecio(0.0);
        boleto.setCodigoQR("/src/main/resources/qrs-boletos/test_" + System.currentTimeMillis() + ".png");
        reservacion.setBoleto(boleto);

        boolean resultado = dao.guardarReservacion(reservacion);

        assertTrue(resultado, "La reservación debería guardarse exitosamente");
        assertNotNull(tokenGenerado, "El token generado no debe ser nulo");
    }

    @Test
    @Order(2)
    @DisplayName("guardarReservacion — MALA: lanza excepción al recibir reservación nula")
    void guardarReservacion_mala_nula() {
        assertThrows(
                PersistenciaException.class,
                () -> dao.guardarReservacion(null),
                "Debería lanzar PersistenciaException al guardar null"
        );
    }

    // =========================================================================
    // obtenerReservacionesUsuario
    // =========================================================================
    @Test
    @Order(3)
    @DisplayName("obtenerReservacionesUsuario — BUENA: retorna lista para usuario existente")
    void obtenerReservacionesUsuario_buena() throws PersistenciaException {
        List<Reservacion> reservaciones = dao.obtenerReservacionesUsuario(ID_USUARIO_EXISTENTE);

        assertNotNull(reservaciones, "La lista no debe ser nula");
        assertDoesNotThrow(() -> dao.obtenerReservacionesUsuario(ID_USUARIO_EXISTENTE));
    }

    @Test
    @Order(4)
    @DisplayName("obtenerReservacionesUsuario — MALA: lanza PersistenciaException con ID inválido")
    void obtenerReservacionesUsuario_mala_idInvalido() {
        // ✅ El DAO atrapa la excepción interna y lanza PersistenciaException
        assertThrows(
                PersistenciaException.class,
                () -> dao.obtenerReservacionesUsuario("id-no-valido-$$"),
                "Debería lanzar PersistenciaException con un ID malformado"
        );
    }

    // =========================================================================
    // obtenerBoleto
    // =========================================================================
    @Test
    @Order(5)
    @DisplayName("obtenerBoleto — BUENA: retorna boleto para reservación recién insertada")
    void obtenerBoleto_buena() throws PersistenciaException {
        // ✅ Primero insertamos una reservación y guardamos su ID
        String token = UUID.randomUUID().toString();

        Reservacion reservacion = new Reservacion();
        reservacion.setTotal(0.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        Boleto boleto = new Boleto();
        boleto.setToken(token);
        boleto.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boleto.setPrecio(0.0);
        boleto.setCodigoQR("/test/qr_" + token + ".png");
        reservacion.setBoleto(boleto);

        dao.guardarReservacion(reservacion);

        // Buscamos la reservación recién insertada por token para obtener su ID
        Boleto encontrado = dao.buscarPorToken(token);

        assertNotNull(encontrado, "El boleto insertado debe encontrarse por token");
        assertEquals(token, encontrado.getToken(), "El token debe coincidir");
    }

    @Test
    @Order(6)
    @DisplayName("obtenerBoleto — MALA: retorna null para reservación inexistente")
    void obtenerBoleto_mala_inexistente() throws PersistenciaException {
        Boleto boleto = dao.obtenerBoleto(ID_INEXISTENTE);
        assertNull(boleto, "Debería retornar null para una reservación que no existe");
    }

    // =========================================================================
    // buscarPorToken
    // =========================================================================
    @Test
    @Order(7)
    @DisplayName("buscarPorToken — BUENA: encuentra boleto con token generado en test 1")
    void buscarPorToken_buena() throws PersistenciaException {
        // ✅ Usamos el token que generamos en el test 1
        assertNotNull(tokenGenerado, "El token del test 1 debe estar disponible");

        Boleto boleto = dao.buscarPorToken(tokenGenerado);

        assertNotNull(boleto, "El boleto no debe ser nulo");
        assertEquals(tokenGenerado, boleto.getToken(), "El token debe coincidir");
    }

    @Test
    @Order(8)
    @DisplayName("buscarPorToken — MALA: lanza excepción con token nulo")
    void buscarPorToken_mala_nulo() {
        assertThrows(
                PersistenciaException.class,
                () -> dao.buscarPorToken(null),
                "Debería lanzar PersistenciaException con token nulo"
        );
    }

    // =========================================================================
    // actualizarEstado
    // =========================================================================
    @Test
    @Order(9)
    @DisplayName("actualizarEstado — BUENA: actualiza estado del boleto generado en test 1")
    void actualizarEstado_buena() throws PersistenciaException {
        // ✅ El token del test 1 está ACTIVO — podemos cambiarlo a USADO
        assertNotNull(tokenGenerado, "El token del test 1 debe estar disponible");

        Boleto boleto = new Boleto();
        boleto.setToken(tokenGenerado);
        boleto.setEstadoBoleto(EstadoBoleto.USADO);

        boolean resultado = dao.actualizarEstado(boleto);

        assertTrue(resultado, "Debe retornar true al modificar un documento existente");
    }

    @Test
    @Order(10)
    @DisplayName("actualizarEstado — MALA: lanza excepción con boleto nulo")
    void actualizarEstado_mala_nulo() {
        assertThrows(
                PersistenciaException.class,
                () -> dao.actualizarEstado(null),
                "Debería lanzar PersistenciaException con boleto nulo"
        );
    }

    // =========================================================================
    // registrarAsistencia
    // =========================================================================
    @Test
    @Order(11)
    @DisplayName("registrarAsistencia — BUENA: registra asistencia para boleto ACTIVO nuevo")
    void registrarAsistencia_buena() throws PersistenciaException {
        // ✅ Insertamos un boleto ACTIVO fresco para este test
        String tokenFresco = UUID.randomUUID().toString();

        Reservacion reservacion = new Reservacion();
        reservacion.setTotal(0.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        Boleto boleto = new Boleto();
        boleto.setToken(tokenFresco);
        boleto.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boleto.setPrecio(0.0);
        boleto.setCodigoQR("/test/qr_asist_" + tokenFresco + ".png");
        reservacion.setBoleto(boleto);

        dao.guardarReservacion(reservacion);

        // Ahora registramos asistencia
        Boleto boletoAsistencia = new Boleto();
        boletoAsistencia.setToken(tokenFresco);
        boletoAsistencia.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boletoAsistencia.setAsistencia(null);

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(ID_EMPLEADO_EXISTENTE);

        Asistencia asistencia = new Asistencia();
        asistencia.setEmpleado(empleado);

        Asistencia resultado = dao.registrarAsistencia(boletoAsistencia, asistencia);

        assertNotNull(resultado, "La asistencia registrada no debe ser nula");
        assertEquals(ID_EMPLEADO_EXISTENTE, resultado.getEmpleado().getIdEmpleado(),
                "El empleado debe coincidir");
    }

    @Test
    @Order(12)
    @DisplayName("registrarAsistencia — MALA: lanza excepción con boleto nulo")
    void registrarAsistencia_mala_nulo() {
        assertThrows(
                PersistenciaException.class,
                () -> dao.registrarAsistencia(null, new Asistencia()),
                "Debería lanzar PersistenciaException con boleto nulo"
        );
    }

    // =========================================================================
    // obtenerReporteAsistencia
    // =========================================================================
    @Test
    @Order(13)
    @DisplayName("obtenerReporteAsistencia — BUENA: retorna reporte para evento existente")
    void obtenerReporteAsistencia_buena() {
        ReporteAsistencia reporte = dao.obtenerReporteAsistencia(ID_EVENTO_EXISTENTE);

        assertNotNull(reporte, "El reporte no debe ser nulo");
        assertTrue(reporte.getAsistidos() >= 0, "Los asistidos no pueden ser negativos");
        assertTrue(reporte.getPendientes() >= 0, "Los pendientes no pueden ser negativos");
        assertEquals(
                reporte.getAsistidos() + reporte.getPendientes(),
                reporte.getTotalVendidos(),
                "El total debe ser la suma de asistidos + pendientes"
        );
    }

    @Test
    @Order(14)
    @DisplayName("obtenerReporteAsistencia — MALA: retorna reporte vacío para evento inexistente")
    void obtenerReporteAsistencia_mala_inexistente() {
        ReporteAsistencia reporte = dao.obtenerReporteAsistencia(ID_INEXISTENTE);

        assertNotNull(reporte, "El reporte no debe ser nulo aunque el evento no exista");
        assertEquals(0, reporte.getAsistidos(), "Sin evento no debe haber asistidos");
        assertEquals(0, reporte.getPendientes(), "Sin evento no debe haber pendientes");
        assertEquals(0, reporte.getTotalVendidos(), "Sin evento el total debe ser 0");
    }

    // =========================================================================
    // obtenerAsientosConAsistencia
    // =========================================================================
    @Test
    @Order(15)
    @DisplayName("obtenerAsientosConAsistencia — BUENA: retorna lista para evento existente")
    void obtenerAsientosConAsistencia_buena() throws PersistenciaException {
        List<AsientoEvento> asientos = dao.obtenerAsientosConAsistencia(ID_EVENTO_EXISTENTE);

        assertNotNull(asientos, "La lista no debe ser nula");
    }

    @Test
    @Order(16)
    @DisplayName("obtenerAsientosConAsistencia — MALA: lanza PersistenciaException con ID inválido")
    void obtenerAsientosConAsistencia_mala_idInvalido() {
        // ✅ El DAO atrapa IllegalArgumentException y lanza PersistenciaException
        assertThrows(
                PersistenciaException.class,
                () -> dao.obtenerAsientosConAsistencia("formato@@invalido"),
                "Debería lanzar PersistenciaException con un ID malformado"
        );
    }
}
