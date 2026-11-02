package StepDefinition;

import ObjectPage.ListaEmpleadosPage;
import ObjectPage.PimPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
//import io.github.bonigarcia.wdm.WebDriverManager;


public class OrangeDefinition {

    private ListaEmpleadosPage listaEmpleadosPage;
    private PimPage pimPage;

    @And("que el usuario inicia sesión en OrangeHRM con el usuario {string} y la contraseña {string}")
    public void iniciaSesion(String usuario, String contrasena) throws InterruptedException {
        listaEmpleadosPage = new ListaEmpleadosPage();
        listaEmpleadosPage.escribirUsersname(usuario);
        listaEmpleadosPage.escribirClaveUsuario(contrasena);
        listaEmpleadosPage.bttLogin();
    }

    @And("el usuario hace clic en el módulo PIM")
    public void moduloPIM() {
        pimPage = new PimPage();
        pimPage.mostrarModuloPIM();
    }

    @When("el usuario ingresa {string} en el campo Nombre")
    public void ingresarNombreEmpleado(String employeeName) {
        pimPage.ingresarNomEmpleado(employeeName);
    }

    @And("hace clic en boton Buscar")
    public void clickBuscar() throws InterruptedException {
        pimPage.botonBuscar();
        Thread.sleep(5000);
    }

    @And("cada resultado debe contener el nombre {string}")
   public void validarBusqNombre(String expectedName) {
        pimPage.validarBusquedaNombre(expectedName);
    }

    @When("el usuario ingresa {string} en el campo Id")
    public void ingresarIdEmpleado(String employeeId) {
        pimPage.ingresarIdEmpleado(employeeId);
    }

    @And("hace click en Buscar")
    public void hacerClickEnBuscar2() throws InterruptedException {
        pimPage.botonBuscar();
        Thread.sleep(5000);
    }

    @Then("se deben mostrar los resultados de la búsqueda {string}")
    public void validarBusqueda(String expectedRecords) {
        pimPage.validarBusquedaId(expectedRecords);
    }

    @When("el usuario se posiciona en filtro")
    public void clickearFiltro() throws InterruptedException {
        pimPage.clicFiltro();
        Thread.sleep(5000);
    }

    @And("selecciona un {string}")
    public void seleccionarEstado(String status) throws InterruptedException {
        pimPage.seleccionStatus(status);
        Thread.sleep(2000);
    }

    @And ("click en Buscar")
    public void buscarFiltro() throws InterruptedException {
        pimPage.botonBuscar();
        Thread.sleep(5000);
    }

    @Then("se debe mostrar los resultados según el {string}")
    public void validarStatus(String status) {
        pimPage.validarResultadosPorStatus(status);
    }

    @When("el usuario se posiciona en opción Include")
            public void clickInclude() throws InterruptedException {
        pimPage.clickIncluir();
        Thread.sleep(2000);
    }

    @And("selecciona un nuevo {string}")
    public void seleccionaInclude(String includeOption) throws InterruptedException {
        pimPage.incluirFiltro(includeOption);
        Thread.sleep(2000);
    }

    @And("clicks en buscar")
    public void buscarInclude() throws InterruptedException {
            pimPage.botonBuscar();
            Thread.sleep(5000);
        }

    @Then("se deben mostrar los resultados de {string}")
    public void validaInclude(String includeOption) {
        pimPage.validarResultadosInclude(includeOption);
    }

    @When("el usuario ingresa con el nombre empleado {string}")
    public void ingresarNombreBusquedaCombinada(String employeeName) {
        pimPage.ingresarNombreParaBusquedaCombinada(employeeName);
    }

    @And("el usuario ingresa el Id {string}")
    public void ingresarIdBusquedaCombinada(String employeeId) {
        pimPage.ingresarIdParaBusquedaCombinada(employeeId);
    }

    @And("el usuario selecciona un {string}")
    public void seleccionarEstadoBusquedaCombinada(String status) {
        pimPage.seleccionarEstadoParaBusquedaCombinada(status);
    }

    @And("el usuario selecciona la opcion {string}")
    public void seleccionarIncludeBusquedaCombinada(String includeOption) {
        pimPage.seleccionarIncludeParaBusquedaCombinada(includeOption);
    }

    @And("el usuario selecciona el nombre del trabajo {string}")
    public void seleccionarTrabajoBusquedaCombinada(String jobTitle) {
        pimPage.seleccionarTrabajo(jobTitle);
    }

    @And("el usuario selecciona una sub Unidad {string}")
    public void seleccionarSubUnidadBusquedaCombinada(String subUnit) {
        pimPage.seleccionarSubUnidad(subUnit);
    }

    @And("el usuario selecciona {string}")
    public void seleccionarSupervisorBusquedaCombinada(String supervisor) {
        pimPage.seleccionarSupervisor(supervisor);
    }

    @And("realiza clic en buscar")
    public void buscarBusquedaCombinada() throws InterruptedException {
        pimPage.buscarYEsperarResultados();
    }

    @Then("cada resultado debe mostrar los criterios seleccionados {string} {string} {string} {string} {string} {string} {string}")
    public void validarBusquedaCombinada(
            String employeeName,
            String employeeId,
            String status,
            String includeOption,
            String jobTitle,
            String subUnit,
            String supervisor) throws InterruptedException {
        pimPage.validarResultadosBusquedaCombinada(
                employeeName, employeeId, status, includeOption, jobTitle, subUnit, supervisor);
        Thread.sleep(3000);
    }

}