package StepDefinition;

import Control.BaseController;
import ObjectPage.LeavePage;
import ObjectPage.ListaEmpleadosPage;
import ObjectPage.PimPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;

public class LeaveDefinition {

    private ListaEmpleadosPage listaEmpleados;
    private LeavePage leavePage;
    private String empleadoSeleccionado;

    @And("el usuario inicia sesión en OrangeHRM con el usuario {string} y la contraseña {string}")
    public void iniciaSesion(String usuario, String contrasena) throws InterruptedException {
        listaEmpleados = new ListaEmpleadosPage();
        listaEmpleados.escribirUsersname(usuario);
        listaEmpleados.escribirClaveUsuario(contrasena);
        listaEmpleados.bttLogin();
    }

    @And("el usuario hace clic en el módulo LEAVE")
    public void moduloLeave() {
        leavePage = new LeavePage();
        leavePage.mostrarModuloLeave();
    }

    @Given("que existen solicitudes de vacaciones")
    public void mostrarVentana() throws InterruptedException {
        leavePage.ventanaLeave();
        Thread.sleep(2000);
    }

    @And("el usuario se encuentra en {string}")
    public void leaveList(String seccion) throws InterruptedException {
        leavePage.seccionLeaveList(seccion);
        Thread.sleep(2500);
    }

    @When("ingresa la fecha inicial {string}")
    public void selecFechaIni(String fechaInicio) throws InterruptedException {
        leavePage.seleccionFechaInicio(fechaInicio);
        Thread.sleep(3000);
    }

    @And("ingresa la fecha final {string}")
    public void selecFechaFin(String fechaFin) throws InterruptedException {
        leavePage.seleccionFechaFin(fechaFin);
        Thread.sleep(3000);
    }

    @And("posicionar en filtro estado")
        public void posicionFiltro() throws InterruptedException {
        leavePage.filtroTipo();
        Thread.sleep(3000);
    }

    @And("selecciona el estado {string}")
    public void seleccionEstado(String estado){
        leavePage.filtrarEstado(estado);
    }

    @And("ingresa el nombre del empleado {string}")
    public void buscarEmpleado(String empleado) throws InterruptedException {
        empleadoSeleccionado = empleado;
        leavePage.nombreEmpleado(empleado);
        Thread.sleep(3000);
    }

    @And("selecciona al empleado desde el autocompletado")
    public void autocompletaEmpleado(){
        if (empleadoSeleccionado == null || empleadoSeleccionado.isBlank()) {
            throw new IllegalStateException(
                    "Debe ingresar el nombre del empleado antes de seleccionar una sugerencia");
        }
        leavePage.seleccionarNombre(empleadoSeleccionado);
    }
    @And("se debe mostrar la validacion {string} si no existe empleado {string}")
    public void validarNombre(String mensaje, String empleado) {
        if (!"Invalid".equals(mensaje)) {
            throw new IllegalArgumentException("La validacion esperada debe ser Invalid: " + mensaje);
        }
        leavePage.seleccionarNombre(empleado);
    }

    @And("selecciona la unidad {string}")
    public void selecSubUnidad(String unidad) throws InterruptedException {
        leavePage.subUnidad(unidad);
        Thread.sleep(3000);
    }

    @And("configura empleados anteriores en {string}")
    public void configurarEmpleadosAnteriores(String anteriores) {
        leavePage.configurarEmpleadosAnteriores(anteriores);
    }

    @And("presiona el botón Buscar")
    public void presionarBuscar() throws InterruptedException {
        leavePage.botonBuscar();
    }

    @Then("se debe completar la búsqueda sin errores")
    public void validarResultadoBusqueda() {
        leavePage.validarResultadoBusqueda();
    }

    @And("se debe visualizar la tabla de resultados")
    public void validarTablaResultados() {
        leavePage.validarTablaResultados();
    }

    //*@And("las solicitudes deben corresponder al empleado {string}")

    @And("las solicitudes deben cumplir el estado {string}")

    @And("las solicitudes deben estar dentro del período consultado")

    @And("las solicitudes deben corresponder a la unidad {string")

    @And("los registros deben respetar el filtro de empleados anteriores")

    @And("no deben existir registros duplicados")

    @And("todas las páginas deben respetar los filtros aplicados")

    @When("el usuario presiona el botón Reset")

    @Then("los filtros deben recuperar sus valores predeterminados")

    @And("no deben permanecer los criterios ingresados anteriormente")*/

}