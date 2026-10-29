package ObjectPage;

import Control.BaseController;
import Control.DriverContext;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class LeavePage extends BaseController {
    public LeavePage() {
        initPage();
    }

    @FindBy(xpath = "//*[@id='app']/div[1]/div[1]/aside/nav/div[2]/ul/li[3]/a/span")
    private WebElement moduloLeave;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]")
    private WebElement ventanaLeave;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]")
    private WebElement seccionLeaveList;

    private static final By FECHA_INICIO =
            By.xpath("//label[normalize-space()='From Date']/following::input[1]");
    private static final By FECHA_FIN =
            By.xpath("//label[normalize-space()='To Date']/following::input[1]");
    private static final DateTimeFormatter FORMATO_FECHA_ENTRADA = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter FORMATO_FECHA_UI = DateTimeFormatter.ofPattern("yyyy-dd-MM");


    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div[1]")
    private WebElement filtroTipo;

    private static final By CAMPO_ESTADO =
            By.xpath("//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form"
                    + "/div[1]/div/div[3]");

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div[2]")
    private WebElement limpiarEtiqueta;

    @FindBy(xpath = "//label[normalize-space()='Employee Name']")
    private WebElement labelEmpleado;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/div/div[1]/div/div[2]/div/div/input")
    private WebElement ingresarEmpleado;

    private static final By SELECTOR_SUB_UNIDAD =
            By.xpath("(//label[normalize-space()='Sub Unit']"
                    + "/following::div[contains(@class,'oxd-select-text')])[1]");

    private static final By CHECK_INCLUDE_PAST_EMPLOYEES =
            By.xpath("//p[normalize-space()='Include Past Employees']"
                    + "/following-sibling::div[contains(@class,'oxd-switch-wrapper')]"
                    + "//input[@type='checkbox']");
    private static final By TOGGLE_INCLUDE_PAST_EMPLOYEES =
            By.xpath("//p[normalize-space()='Include Past Employees']"
                    + "/following-sibling::div[contains(@class,'oxd-switch-wrapper')]"
                    + "//span[contains(@class,'oxd-switch-input')]");

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[3]/button[2]")
    private WebElement botonBuscar;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[3]/button[1]")
    private WebElement botonReset;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]")
    private WebElement resultados;


    public void mostrarModuloLeave() {
        try {
            if (!visualizarElemento(this.moduloLeave, 10)) {
                throw new AssertionError("No se encuentra visible el módulo LEAVE");
            }
            this.moduloLeave.click();
        } catch (Exception e) {
            Assert.fail("Error al ingresar al módulo LEAVE: " + e.getMessage());
        }
    }

    public void ventanaLeave() {
        if (!visualizarElemento(this.ventanaLeave, 10)) {
            throw new AssertionError("No se encuentra visible la página Leave List");
        }
    }

    public void seccionLeaveList(String seccionEsperada) {
        if (!visualizarElemento(this.seccionLeaveList, 10)) {
            throw new AssertionError("No se encuentra visible");
        }
        Assert.assertTrue(
                "La sección visible no corresponde a: " + seccionEsperada,
                this.seccionLeaveList.getText().contains(seccionEsperada));
    }

    public void seleccionFechaInicio(String fechaInicio) {
        ingresarFecha(FECHA_INICIO, fechaInicio, "From Date");
    }

    public void seleccionFechaFin(String fechaFin) {
        ingresarFecha(FECHA_FIN, fechaFin, "To Date");
    }

    private void ingresarFecha(By localizador, String fecha, String nombreCampo) {
        String fechaUI = LocalDate.parse(fecha, FORMATO_FECHA_ENTRADA).format(FORMATO_FECHA_UI);
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(15));
        WebElement campoFecha = wait.until(ExpectedConditions.elementToBeClickable(localizador));
        campoFecha.click();
        campoFecha.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        campoFecha.sendKeys(Keys.DELETE);
        campoFecha.sendKeys(fechaUI);
        campoFecha.sendKeys(Keys.TAB);

        String valorIngresado = campoFecha.getAttribute("value");
        Assert.assertEquals(
                "La fecha ingresada no coincide en el campo " + nombreCampo,
                fechaUI,
                valorIngresado);
    }

    public void filtroTipo(){
        if(!visualizarElemento(this.filtroTipo, 10)){
            this.filtroTipo.click();
        }
    }

    public void filtrarEstado(String estado) {
        if (!visualizarElemento(this.limpiarEtiqueta, 10)) {
            limpiarEtiqueta.clear();
        }
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        WebElement campoEstado = wait.until(
                ExpectedConditions.visibilityOfElementLocated(CAMPO_ESTADO));

        By etiquetasEstado = By.cssSelector(".oxd-chip");
        if (campoEstado.findElements(etiquetasEstado).stream()
                .anyMatch(etiqueta -> etiqueta.isDisplayed()
                        && etiqueta.getText().trim().equals(estado))) {
            return;
        }

        WebElement selectorEstado = campoEstado.findElement(
                By.cssSelector(".oxd-multiselect-wrapper .oxd-select-text"));
        wait.until(ExpectedConditions.elementToBeClickable(selectorEstado)).click();

        By opcionesEstado = By.cssSelector(".oxd-select-option");
        boolean opcionVisible = wait.until(driver -> driver.findElements(opcionesEstado).stream()
                .anyMatch(opcion -> opcion.isDisplayed()
                        && opcion.getText().trim().equals(estado)));
        if (!opcionVisible) {
            throw new AssertionError("No se encontró la opción de estado: " + estado);
        }

        WebElement opcionEstado = DriverContext.getDriver().findElements(opcionesEstado).stream()
                .filter(opcion -> opcion.isDisplayed()
                        && opcion.getText().trim().equals(estado))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "No se encuentra la opción de estado: " + estado));
        opcionEstado.click();

        wait.until(driver -> driver.findElement(CAMPO_ESTADO).findElements(etiquetasEstado).stream()
                .anyMatch(etiqueta -> etiqueta.isDisplayed()
                        && etiqueta.getText().trim().equals(estado)));
    }

    public void nombreEmpleado(String empleado) {
        try {
            if (!visualizarElemento(this.labelEmpleado, 10)
                    || !visualizarElemento(this.ingresarEmpleado, 10)) {
                throw new AssertionError("No se encuentra el campo Employee Name en la página");
            }
            this.ingresarEmpleado.clear();
            this.ingresarEmpleado.sendKeys(empleado);
        } catch (Exception e) {
            throw new AssertionError("Error al escribir en el campo: " + e.getMessage(), e);
        }
    }

    public void seleccionarNombre(String empleado) {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(15));
        By sugerencias = By.cssSelector(".oxd-autocomplete-option");
        WebElement campoEmpleado = wait.until(
                ExpectedConditions.elementToBeClickable(this.ingresarEmpleado));
        String valorIngresado = normalizarTexto(campoEmpleado.getAttribute("value"));
        if (!valorIngresado.contains(normalizarTexto(empleado))) {
            throw new AssertionError("El campo Employee Name contiene '" + valorIngresado
                    + "', no el nombre esperado '" + empleado + "'.");
        }
        try {
            wait.until(driver -> driver.findElements(sugerencias).stream()
                    .anyMatch(opcion -> opcion.isDisplayed()
                            && normalizarTexto(opcion.getText())
                            .contains(normalizarTexto(empleado))));
        } catch (TimeoutException e) {
            List<String> opcionesVisibles = DriverContext.getDriver().findElements(sugerencias).stream()
                    .filter(WebElement::isDisplayed)
                    .map(WebElement::getText)
                    .toList();
            throw new AssertionError("No se encontró una sugerencia para Employee Name: "
                    + empleado + ". Sugerencias visibles: " + opcionesVisibles, e);
        }
        By mensajeError = By.xpath("//label[normalize-space()='Employee Name']"
                + "/ancestor::div[contains(@class,'oxd-input-group')][1]"
                + "//span[contains(@class,'oxd-input-field-error-message')]");
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeError));

        Assert.assertEquals("El campo Employee Name no conserva el texto ingresado.",
                empleado, this.ingresarEmpleado.getAttribute("value"));
        Assert.assertEquals("No se muestra la validacion esperada para Employee Name.",
                "Invalid", error.getText().trim());
        wait.until(driver -> normalizarTexto(this.ingresarEmpleado.getAttribute("value"))
                .contains(normalizarTexto(empleado)));
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
    }

    public void subUnidad(String unidad) {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        WebElement selector = wait.until(
                ExpectedConditions.elementToBeClickable(SELECTOR_SUB_UNIDAD));
        WebElement valorActual = selector.findElement(By.cssSelector(".oxd-select-text-input"));
        if (valorActual.getText().trim().equals(unidad)) {
            return;
        }
        selector.click();
        By opciones = By.cssSelector(".oxd-select-option");
        wait.until(driver -> driver.findElements(opciones).stream()
                .anyMatch(opcion -> opcion.isDisplayed()
                        && opcion.getText().trim().equals(unidad)));

        WebElement opcion = DriverContext.getDriver().findElements(opciones).stream()
                .filter(elemento -> elemento.isDisplayed()
                        && elemento.getText().trim().equals(unidad))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "No se encontró la subunidad: " + unidad));
        opcion.click();

        wait.until(driver -> driver.findElement(SELECTOR_SUB_UNIDAD)
                .findElement(By.cssSelector(".oxd-select-text-input"))
                .getText().trim().equals(unidad));
    }

    public void configurarEmpleadosAnteriores(String anteriores) {
        if (!"Yes".equalsIgnoreCase(anteriores) && !"No".equalsIgnoreCase(anteriores)) {
            throw new IllegalArgumentException(
                    "El valor de Include Past Employees debe ser Yes o No: " + anteriores);
        }

        boolean incluirAnteriores = "Yes".equalsIgnoreCase(anteriores);
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(CHECK_INCLUDE_PAST_EMPLOYEES));
        if (checkbox.isSelected() != incluirAnteriores) {
            WebElement toggle = wait.until(
                    ExpectedConditions.elementToBeClickable(TOGGLE_INCLUDE_PAST_EMPLOYEES));
            toggle.click();
        }

        wait.until(driver -> driver.findElement(CHECK_INCLUDE_PAST_EMPLOYEES).isSelected()
                == incluirAnteriores);
    }

    public void botonBuscar()throws InterruptedException {
        this.botonBuscar.click();
    }

    public void validarResultadoBusqueda() {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(15));
        By cuerpoGrilla = By.cssSelector(".oxd-table-body");
        By filasResultado = By.cssSelector(".oxd-table-body .oxd-table-card");
        By indicadorCarga = By.cssSelector(".oxd-loading-spinner-container");
        By mensajeSinResultados = By.xpath("//*[normalize-space()='No Records Found']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(cuerpoGrilla));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(indicadorCarga));

        List<WebElement> filas = DriverContext.getDriver().findElements(filasResultado).stream()
                .filter(WebElement::isDisplayed)
                .toList();
        if (!filas.isEmpty()) {
            System.out.println("La búsqueda devolvió " + filas.size() + " solicitud(es).");
            return;
        }

        boolean sinResultados = DriverContext.getDriver().findElements(mensajeSinResultados).stream()
                .anyMatch(WebElement::isDisplayed);
        Assert.assertTrue(
                "La búsqueda no muestra filas ni el mensaje 'No Records Found'.",
                sinResultados);
        System.out.println("La búsqueda finalizó correctamente: No Records Found.");
    }

    public void validarTablaResultados() {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-table-header")));
        List<WebElement> encabezados = DriverContext.getDriver()
                .findElements(By.cssSelector(".oxd-table-header .oxd-table-cell")).stream()
                .filter(WebElement::isDisplayed)
                .toList();
        Assert.assertFalse("La tabla de resultados no muestra encabezados.", encabezados.isEmpty());
    }

}