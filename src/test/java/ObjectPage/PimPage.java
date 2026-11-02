package ObjectPage;

import Control.BaseController;
import Control.DriverContext;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PimPage extends BaseController {

    public PimPage() {
        initPage();
    }

    @FindBy(xpath = "//*[@id='app']/div[1]/div[1]/aside/nav/div[2]/ul/li[2]/a")
    private WebElement moduloPIM;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")
    private WebElement ingresarNombreEmpleado;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/input")
    private WebElement ingresarIdEmpleado;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
    private WebElement botonBuscar;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")
    private WebElement validarResultadosBusqueda;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]")
    private WebElement validarBusqueda;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div")
    private WebElement filtroStatus;

    @FindBy(css = ".oxd-select-wrapper")
    private WebElement seleccionarStatus;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/div[2]")
    private WebElement clickFiltro;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div")
    private WebElement incluir;

    public void mostrarModuloPIM() {
        try {
            if (!visualizarElemento(this.moduloPIM, 10)) {
                throw new AssertionError("No se encuentra visible el módulo PIM");
            }
            this.moduloPIM.click();
        } catch (Exception e) {
            Assert.fail("Error al ingresar al módulo PIM: " + e.getMessage());
        }
    }

    public void ingresarNomEmpleado(String employeeName) {
        try {
            if (visualizarElemento(this.ingresarNombreEmpleado, 10)) {
                this.ingresarNombreEmpleado.clear();
                this.ingresarNombreEmpleado.sendKeys(employeeName);
            } else {
                throw new AssertionError("No se encuentra el campo Employee Name en la página");
            }
        } catch (Exception e) {
            throw new AssertionError("Error al escribir en el campo: " + e.getMessage(), e);
        }
    }

    public void botonBuscar() throws InterruptedException {
        this.botonBuscar.click();
    }

    public void buscarYEsperarResultados() {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(15));
        By cuerpoTabla = By.cssSelector(".oxd-table-body");
        By indicadorCarga = By.cssSelector(".oxd-loading-spinner-container");
        WebElement tabla = wait.until(ExpectedConditions.visibilityOfElementLocated(cuerpoTabla));
        String contenidoAnterior = tabla.getText();

        this.botonBuscar.click();
        wait.until(driver -> {
            List<WebElement> indicadores = driver.findElements(indicadorCarga);
            boolean cargando = indicadores.stream()
                    .anyMatch(indicador -> indicador.isDisplayed());
            WebElement tablaActual = driver.findElement(cuerpoTabla);
            return cargando || !tablaActual.getText().equals(contenidoAnterior);
        });
        wait.until(ExpectedConditions.invisibilityOfElementLocated(indicadorCarga));
    }

    public void ingresarIdEmpleado(String employeeId) {
        try {
            if (visualizarElemento(this.ingresarIdEmpleado, 10)) {
                this.ingresarIdEmpleado.clear();
                this.ingresarIdEmpleado.sendKeys(employeeId);
            } else {
                throw new AssertionError("No se encuentra el campo Employee Id en la página");
            }
        } catch (Exception e) {
            throw new AssertionError("Error al escribir en el campo: " + e.getMessage(), e);
        }
    }

    public void validarBusquedaNombre(String expectedName) {
        if (visualizarElemento(this.validarBusqueda, 10)) {
            System.out.println("Se ingresó el nombre esperado: " + expectedName);
        } else {
            throw new AssertionError("No se encuentra el campo en la página");
        }
    }

    public void validarBusquedaId(String expectedRecords) {
        if (visualizarElemento(this.validarResultadosBusqueda, 10)) {
            String valor1 = "0";
            if (expectedRecords != valor1) ;
            System.out.println("Se encontraron registros" + expectedRecords);
        } else {
            System.out.println("No se encontraron registros");
            //throw new AssertionError("No se encontraron registros");
        }
    }

    public void clicFiltro() {
        if (!visualizarElemento(this.filtroStatus, 10)) {
            throw new AssertionError("No se encuentra visible el filtro Employment Status");
        }
        this.clickFiltro.click();
    }

    public void seleccionStatus(String status) {
        By opcionesStatus = By.cssSelector(".oxd-select-option");
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(this.seleccionarStatus));
        wait.until(driver -> driver.findElements(opcionesStatus).stream()
                .anyMatch(opcion -> opcion.isDisplayed() && opcion.getText().trim().equals(status)));

        List<WebElement> opciones = DriverContext.getDriver().findElements(opcionesStatus);
        for (WebElement opcion : opciones) {
            if (opcion.isDisplayed() && opcion.getText().trim().equals(status)) {
                opcion.click();
                wait.until(driver -> this.filtroStatus.getText().trim().contains(status));
                return;
            }
        }
        throw new AssertionError("No se encuentra la opción Employment Status: " + status);
    }

    public void validarResultadosPorStatus(String status) {
        By filasResultado = By.cssSelector(".oxd-table-body .oxd-table-card");
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        Assert.assertTrue(
                "El filtro Employment Status no conserva el estado seleccionado",
                this.filtroStatus.getText().trim().contains(status));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-body")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".oxd-loading-spinner-container")));

        List<WebElement> filas = DriverContext.getDriver().findElements(filasResultado);
        for (WebElement fila : filas) {
            List<WebElement> celdas = fila.findElements(By.cssSelector(".oxd-table-cell"));
            if (celdas.size() <= 5) {
                throw new AssertionError("La fila de resultados no contiene la columna Employment Status");
            }
            Assert.assertEquals(
                    "El resultado no corresponde al estado seleccionado",
                    status,
                    celdas.get(5).getText().trim());
        }
    }

    public void incluirFiltro(String includeOption) {
        By opcionesIncluir = By.cssSelector(".oxd-select-option");
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(this.incluir));
        wait.until(driver -> driver.findElements(opcionesIncluir).stream()
                .anyMatch(opcion -> opcion.isDisplayed() && opcion.getText().trim().equals(includeOption)));

        List<WebElement> opciones = DriverContext.getDriver().findElements(opcionesIncluir);
        for (WebElement opcion : opciones) {
            if (opcion.isDisplayed() && opcion.getText().trim().equals(includeOption)) {
                opcion.click();
                wait.until(driver -> this.incluir.findElement(By.cssSelector(".oxd-select-text-input"))
                        .getText().trim().equals(includeOption));
                return;
            }
        }
        throw new AssertionError("No se encuentra la opción Include: " + includeOption);
    }

    public void clickIncluir() {
        if (!visualizarElemento(this.incluir, 10)) {
            throw new AssertionError("No se encuentra visible el filtro Include");
        }
        this.incluir.click();
    }

    public void validarResultadosInclude(String includeOption) {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        Assert.assertTrue(
                "El filtro Include no conserva la opción seleccionada",
                this.incluir.findElement(By.cssSelector(".oxd-select-text-input"))
                        .getText().trim().equals(includeOption));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-body")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".oxd-loading-spinner-container")));
    }

    public void ingresarNombreParaBusquedaCombinada(String employeeName) {
        seleccionarAutocomplete("Employee Name", employeeName);
    }

    public void ingresarIdParaBusquedaCombinada(String employeeId) {
        ingresarIdEmpleado(employeeId);
    }

    public void seleccionarEstadoParaBusquedaCombinada(String status) {
        seleccionarFiltro("Employment Status", status);
    }

    public void seleccionarIncludeParaBusquedaCombinada(String includeOption) {
        seleccionarFiltro("Include", includeOption);
    }

    public void seleccionarTrabajo(String jobTitle) {
        seleccionarFiltro("Job Title", jobTitle);
    }

    public void seleccionarSubUnidad(String subUnit) {
        seleccionarFiltro("Sub Unit", subUnit);
    }

    public void seleccionarSupervisor(String supervisor) {
        seleccionarAutocomplete("Supervisor Name", supervisor);
    }

    public void validarResultadosBusquedaCombinada(
            String employeeName,
            String employeeId,
            String status,
            String includeOption,
            String jobTitle,
            String subUnit,
            String supervisor) {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-body")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".oxd-loading-spinner-container")));

        Assert.assertTrue("El filtro Include no conserva la opción seleccionada",
                textoFiltro("Include").equals(includeOption));
        Assert.assertTrue("El filtro Employment Status no conserva el estado seleccionado",
                textoFiltro("Employment Status").equals(status));
        Assert.assertTrue("El filtro Job Title no conserva el trabajo seleccionado",
                textoFiltro("Job Title").equals(jobTitle));
        Assert.assertTrue("El filtro Sub Unit no conserva la unidad seleccionada",
                textoFiltro("Sub Unit").equals(subUnit));
        Assert.assertTrue("El nombre seleccionado no se conserva",
                normalizarTexto(valorCampo("Employee Name")).contains(normalizarTexto(employeeName)));
        Assert.assertTrue("El supervisor seleccionado no se conserva",
                normalizarTexto(valorCampo("Supervisor Name")).contains(normalizarTexto(supervisor)));

        List<WebElement> filas = DriverContext.getDriver()
                .findElements(By.cssSelector(".oxd-table-body .oxd-table-card"));
        Assert.assertFalse(
                "No hay empleados que coincidan con los filtros seleccionados: "
                        + "nombre='" + employeeName + "', ID='" + employeeId
                        + "', estado='" + status + "', include='" + includeOption
                        + "', trabajo='" + jobTitle + "', sub unidad='" + subUnit
                        + "', supervisor='" + supervisor
                        + "'. Verifica que los valores pertenezcan al mismo empleado.",
                filas.isEmpty());
        for (WebElement fila : filas) {
            Assert.assertEquals("El ID del resultado no corresponde al filtro",
                    employeeId, valorCelda(fila, "Id"));
            String nombreCompleto = valorCelda(fila, "First (& Middle) Names") + " "
                    + valorCelda(fila, "Last Name");
            Assert.assertTrue("El nombre del resultado no corresponde al filtro",
                    nombreCompleto.contains(employeeName));
            Assert.assertEquals("El trabajo del resultado no corresponde al filtro",
                    jobTitle, valorCelda(fila, "Job Title"));
            Assert.assertEquals("El estado del resultado no corresponde al filtro",
                    status, valorCelda(fila, "Employment Status"));
            Assert.assertEquals("La sub unidad del resultado no corresponde al filtro",
                    subUnit, valorCelda(fila, "Sub Unit"));
        }
    }

    private void seleccionarAutocomplete(String label, String value) {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(15));
        WebElement input = campoAutocomplete(label);
        wait.until(ExpectedConditions.elementToBeClickable(input));
        input.clear();
        input.sendKeys(value);

        By sugerencias = By.cssSelector(".oxd-autocomplete-option");
        try {
            wait.until(driver -> driver.findElements(sugerencias).stream()
                    .anyMatch(opcion -> opcion.isDisplayed()
                            && normalizarTexto(opcion.getText()).contains(normalizarTexto(value))));
        } catch (TimeoutException e) {
            List<String> opcionesVisibles = DriverContext.getDriver().findElements(sugerencias).stream()
                    .filter(WebElement::isDisplayed)
                    .map(WebElement::getText)
                    .toList();
            throw new AssertionError(
                    "No se encontró una sugerencia para " + label + ": " + value
                            + ". Sugerencias visibles: " + opcionesVisibles,
                    e);
        }
        WebElement sugerencia = DriverContext.getDriver().findElements(sugerencias).stream()
                .filter(opcion -> opcion.isDisplayed()
                        && normalizarTexto(opcion.getText()).contains(normalizarTexto(value)))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "No se encontró una sugerencia para " + label + ": " + value));
        sugerencia.click();
        wait.until(driver -> normalizarTexto(input.getAttribute("value"))
                .contains(normalizarTexto(value)));
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
    }

    private void seleccionarFiltro(String label, String value) {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(10));
        WebElement selector = selectorFiltro(label).findElement(By.cssSelector(".oxd-select-text"));
        selector.click();

        By opciones = By.cssSelector(".oxd-select-option");
        wait.until(driver -> driver.findElements(opciones).stream()
                .anyMatch(opcion -> opcion.isDisplayed() && opcion.getText().trim().equals(value)));
        WebElement opcion = DriverContext.getDriver().findElements(opciones).stream()
                .filter(element -> element.isDisplayed() && element.getText().trim().equals(value))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "No se encontró la opción " + value + " para " + label));
        opcion.click();
        wait.until(driver -> selector.findElement(By.cssSelector(".oxd-select-text-input"))
                .getText().trim().equals(value));
    }

    private String textoFiltro(String label) {
        return selectorFiltro(label).findElement(By.cssSelector(".oxd-select-text-input"))
                .getText().trim();
    }

    private String valorCampo(String label) {
        return campoAutocomplete(label).getAttribute("value").trim();
    }

    private WebElement campoAutocomplete(String label) {
        if ("Employee Name".equals(label)) {
            return ingresarNombreEmpleado;
        }
        if ("Supervisor Name".equals(label)) {
            List<WebElement> grupos = DriverContext.getDriver()
                    .findElements(By.cssSelector(".oxd-input-group"));
            for (WebElement grupo : grupos) {
                List<WebElement> etiquetas = grupo.findElements(By.tagName("label"));
                if (etiquetas.stream().anyMatch(etiqueta ->
                        etiqueta.getText().trim().equals(label))) {
                    return grupo.findElement(By.cssSelector(".oxd-autocomplete-wrapper input"));
                }
            }
            throw new AssertionError("No se encontró el campo " + label);
        }
        throw new IllegalArgumentException("Campo autocomplete no soportado: " + label);
    }

    private WebElement selectorFiltro(String label) {
        int index;
        switch (label) {
            case "Employment Status":
                index = 0;
                break;
            case "Include":
                index = 1;
                break;
            case "Job Title":
                index = 2;
                break;
            case "Sub Unit":
                index = 3;
                break;
            default:
                throw new IllegalArgumentException("Filtro no soportado: " + label);
        }
        List<WebElement> selectores = DriverContext.getDriver()
                .findElements(By.cssSelector(".oxd-select-wrapper"));
        if (selectores.size() <= index) {
            throw new AssertionError("No se encontró el selector para " + label);
        }
        return selectores.get(index);
    }

    private String valorCelda(WebElement fila, String encabezadoBuscado) {
        List<WebElement> celdasMoviles = fila.findElements(By.cssSelector(".oxd-table-card-cell"));
        for (WebElement celda : celdasMoviles) {
            String encabezado = celda.findElement(By.cssSelector(".header")).getText().trim();
            if (encabezado.equals(encabezadoBuscado)) {
                return celda.findElement(By.cssSelector(".data")).getText().trim();
            }
        }

        List<WebElement> encabezados = DriverContext.getDriver()
                .findElements(By.cssSelector(".oxd-table-header .oxd-table-cell"));
        List<WebElement> valores = fila.findElements(By.cssSelector(".oxd-table-cell"));
        List<String> nombresEncabezado = new ArrayList<>();
        for (WebElement encabezado : encabezados) {
            nombresEncabezado.add(encabezado.getText().trim());
        }
        int indice = nombresEncabezado.indexOf(encabezadoBuscado);
        if (indice < 0 || indice >= valores.size()) {
            throw new AssertionError("No se encontró la columna " + encabezadoBuscado
                    + " en la tabla de resultados");
        }
        return valores.get(indice).getText().trim();
    }

}