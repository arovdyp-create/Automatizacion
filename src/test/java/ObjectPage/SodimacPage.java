package ObjectPage;

import Control.BaseController;
import Control.DriverContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SodimacPage extends BaseController {

    private static final String NAVIDAD_PATH = "/lista/cat3640002/Navidad";

    private static final By MENU = By.cssSelector(
            "[data-testid='SiSNavigationMenuId'] [data-testid='CategoryButton']");

    private static final By TITULO_NAVIDAD = By.xpath(
            "//*[self::h1 or self::h2 or self::h3][normalize-space()='Navidad']");

    private static final By NAVIDAD = By.xpath("//*[normalize-space()='Navidad']");
    private static final By TODO_NAVIDAD = By.cssSelector(
            "a[href*='/lista/cat3640002/Navidad']");
    private static final By TARJETAS_PRODUCTO = By.cssSelector(
            "#testId-searchResults-products [data-testid='ssr-pod']");

    @FindBy(xpath = "//*[self::h1 or self::h2 or self::h3][normalize-space()='Navidad']")
    private WebElement tituloCategoria;

    @FindBy(xpath = "//*[@id=\"__next\"]/div[1]/div[2]/div[3]/section[2]")
    private WebElement contenedorProductos;

    @FindBy(xpath="//*[@id=\"testId-searchResults-products\"]/div[1]")
    private WebElement descripcionProducto;

    private List<WebElement> tarjetasProductoCache;

    public SodimacPage() {
        initPage();
    }

    public void desplegarMenu() {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(20));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(MENU));
        new Actions(DriverContext.getDriver())
                .moveToElement(menu)
                .click()
                .perform();
        esperarElementoVisible(wait, NAVIDAD);
    }

    public void moduloNavidad() {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(20));
        esperarElementoVisible(wait, NAVIDAD).click();
    }

    public void subMenuNavidad() {
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(20));
        esperarElementoVisible(wait, TODO_NAVIDAD).click();
        wait.until(ExpectedConditions.urlContains(NAVIDAD_PATH));
    }

    private WebElement esperarElementoVisible(WebDriverWait wait, By locator) {
        return wait.until(driver -> driver.findElements(locator).stream()
                .filter(WebElement::isDisplayed)
                .filter(WebElement::isEnabled)
                .findFirst()
                .orElse(null));
    }

    public void verPagina() {
        DriverContext.getDriver().manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains(NAVIDAD_PATH));
        wait.until(driver -> "complete".equals(
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.readyState")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TITULO_NAVIDAD));
        wait.until(ExpectedConditions.visibilityOf(contenedorProductos));
        System.out.println("Página Navidad cargada");
    }


    public void verTitulo(){
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TITULO_NAVIDAD));
        System.out.println("Se visualiza el título de la categoría Navidad");
    }

    public void tituloCategoria(){
        if (visualizarElemento(this.tituloCategoria, 10)) {
        boolean displayed = this.tituloCategoria.isDisplayed();
        System.out.println("Se visualiza la categoria");
    } else {
        throw new AssertionError("No se encuentra la categoria");
    }
}
public void verContenedor(){
    WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(20));
    wait.until(ExpectedConditions.visibilityOf(contenedorProductos));
    System.out.println("Se visualiza el contenedor de productos");
}

    public void validarTarjetasProducto(List<String> camposEsperados) {
        List<WebElement> tarjetas = tarjetasProductoEnPagina();

        for (int i = 0; i < tarjetas.size(); i++) {
            WebElement tarjeta = tarjetas.get(i);
            for (String campo : camposEsperados) {
                boolean valido;
                switch (campo.trim().toLowerCase()) {
                    case "imagen":
                        valido = tieneImagenCargada(tarjeta);
                        break;
                    case "nombre":
                        valido = !obtenerNombreProducto(tarjeta).isBlank();
                        break;
                    case "precio":
                        valido = tienePrecioCLP(tarjeta);
                        break;
                    case "enlace de producto":
                        valido = obtenerEnlaceProducto(tarjeta) != null;
                        break;
                    default:
                        throw new IllegalArgumentException("Campo de tarjeta no soportado: " + campo);
                }

                if (!valido) {
                    throw new AssertionError("La tarjeta de producto visible #" + (i + 1)
                            + " no muestra un campo válido: " + campo);
                }
            }
        }
        System.out.println("Se validaron " + tarjetas.size()
                + " tarjetas visibles con los campos: " + camposEsperados);
    }

    public void validarImagenesProducto() {
        List<WebElement> tarjetas = tarjetasProductoEnPagina();
        for (int i = 0; i < tarjetas.size(); i++) {
            if (!tieneImagenCargada(tarjetas.get(i))) {
                throw new AssertionError("La tarjeta visible #" + (i + 1)
                        + " no tiene una imagen cargada correctamente");
            }
        }
        System.out.println("Validación correcta: las imágenes de las "
                + tarjetas.size() + " tarjetas visibles cargaron correctamente.");
    }

    public void validarNombresProducto() {
        List<WebElement> tarjetas = tarjetasProductoEnPagina();
        for (int i = 0; i < tarjetas.size(); i++) {
            if (obtenerNombreProducto(tarjetas.get(i)).isBlank()) {
                throw new AssertionError("La tarjeta visible #" + (i + 1)
                        + " tiene el nombre vacío");
            }
        }
        System.out.println("Validación correcta: las " + tarjetas.size()
                + " tarjetas visibles tienen nombre.");
    }

    public void validarPreciosCLP() {
        List<WebElement> tarjetas = tarjetasProductoEnPagina();
        for (int i = 0; i < tarjetas.size(); i++) {
            if (!tienePrecioCLP(tarjetas.get(i))) {
                throw new AssertionError("La tarjeta visible #" + (i + 1)
                        + " no muestra un precio con formato CLP (ejemplo: $ 12.990)");
            }
        }
        System.out.println("Validación correcta: las " + tarjetas.size()
                + " tarjetas visibles muestran precios con formato CLP.");
    }

    public void validarTarjetasSinSuperposicion() {
        List<WebElement> tarjetas = tarjetasProductoEnPagina();
        for (int i = 0; i < tarjetas.size(); i++) {
            for (int j = i + 1; j < tarjetas.size(); j++) {
                boolean seSuperponen = Boolean.TRUE.equals(
                        ((org.openqa.selenium.JavascriptExecutor) DriverContext.getDriver())
                                .executeScript(
                                        "const a = arguments[0].getBoundingClientRect();"
                                                + "const b = arguments[1].getBoundingClientRect();"
                                                + "return Math.min(a.right, b.right) - Math.max(a.left, b.left) > 1"
                                                + " && Math.min(a.bottom, b.bottom) - Math.max(a.top, b.top) > 1;",
                                        tarjetas.get(i), tarjetas.get(j)));
                if (seSuperponen) {
                    throw new AssertionError("Las tarjetas visibles #" + (i + 1)
                            + " y #" + (j + 1) + " se superponen");
                }
            }
        }
        System.out.println("Validación correcta: no hay superposición entre las "
                + tarjetas.size() + " tarjetas visibles.");
    }

    public void validarTarjetasSinDuplicados() {
        List<WebElement> tarjetas = tarjetasProductoEnPagina();
        Set<String> enlacesVistos = new HashSet<>();
        for (int i = 0; i < tarjetas.size(); i++) {
            WebElement enlace = obtenerEnlaceProducto(tarjetas.get(i));
            if (enlace == null) {
                throw new AssertionError("No se pudo identificar el producto de la tarjeta visible #"
                        + (i + 1) + " para comprobar duplicados");
            }

            String urlProducto = enlace.getAttribute("href")
                    .split("\\?", 2)[0]
                    .replaceAll("/+$", "")
                    .toLowerCase();
            if (!enlacesVistos.add(urlProducto)) {
                String nombreProducto = obtenerNombreProducto(tarjetas.get(i));
                System.out.println("ADVERTENCIA: producto duplicado en la tarjeta visible #"
                        + (i + 1) + ": " + nombreProducto + " (" + urlProducto + ")");
            }
        }
        System.out.println("Revisión de duplicados completada para "
                + tarjetas.size() + " tarjetas visibles.");
    }

    private List<WebElement> tarjetasProductoEnPagina() {
        if (tarjetasProductoCache != null) {
            return tarjetasProductoCache;
        }

        WebDriver driver = DriverContext.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> !d.findElements(TARJETAS_PRODUCTO).isEmpty());

        org.openqa.selenium.JavascriptExecutor js =
                (org.openqa.selenium.JavascriptExecutor) driver;
        int maxScrolls = 1000;
        boolean llegoAlFinal = false;
        int lecturasEstablesAlFinal = 0;
        String estadoAnterior = "";

        for (int i = 0; i < maxScrolls; i++) {
            js.executeScript(
                    "const step = Math.max(300, window.innerHeight * 0.6);"
                            + "window.scrollTo(0, window.scrollY + step);");
            pausarCargaProductos(350);

            String estadoActual = js.executeScript(
                    "return document.documentElement.scrollHeight + ':'"
                            + " + document.querySelectorAll("
                            + "'#testId-searchResults-products [data-testid=\"ssr-pod\"]')"
                            + ".length;").toString();
            boolean alFinal = Boolean.TRUE.equals(js.executeScript(
                    "return window.scrollY + window.innerHeight"
                            + " >= document.documentElement.scrollHeight - 2;"));

            if (alFinal) {
                llegoAlFinal = true;
                if (estadoActual.equals(estadoAnterior)) {
                    lecturasEstablesAlFinal++;
                } else {
                    lecturasEstablesAlFinal = 0;
                }
                if (lecturasEstablesAlFinal >= 3) {
                    break;
                }
            } else {
                llegoAlFinal = false;
                lecturasEstablesAlFinal = 0;
            }
            estadoAnterior = estadoActual;
        }

        if (!llegoAlFinal || lecturasEstablesAlFinal < 3) {
            throw new AssertionError(
                    "No se alcanzó el final estable de la página de productos después de "
                            + maxScrolls + " desplazamientos");
        }

        js.executeScript("window.scrollTo(0, 0);");
        tarjetasProductoCache = driver.findElements(TARJETAS_PRODUCTO).stream()
                .filter(WebElement::isDisplayed)
                .toList();
        if (tarjetasProductoCache.isEmpty()) {
            throw new AssertionError("No se encontraron tarjetas de producto en la página");
        }
        System.out.println("Se cargaron " + tarjetasProductoCache.size()
                + " tarjetas con desplazamiento gradual.");
        return tarjetasProductoCache;
    }

    private void pausarCargaProductos(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Se interrumpió la espera durante la carga de productos", e);
        }
    }

    private boolean tieneImagenCargada(WebElement tarjeta) {
        List<WebElement> imagenes = tarjeta.findElements(
                By.cssSelector("a[href*='/articulo/'] img"));
        if (imagenes.isEmpty()) {
            imagenes = tarjeta.findElements(By.cssSelector("img"));
        }

        for (WebElement imagen : imagenes) {
            try {
                new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(3))
                        .until(driver -> imagen.isDisplayed()
                                && Boolean.TRUE.equals(
                                ((org.openqa.selenium.JavascriptExecutor) driver)
                                        .executeScript(
                                                "return arguments[0].complete"
                                                        + " && arguments[0].naturalWidth > 1"
                                                        + " && arguments[0].naturalHeight > 1"
                                                        + " && !!(arguments[0].currentSrc"
                                                        + " || arguments[0].src);",
                                                imagen)));
                return true;
            } catch (org.openqa.selenium.TimeoutException ignored) {
                // Other images in the same card may be the product image.
            }
        }
        return false;
    }

    private String obtenerNombreProducto(WebElement tarjeta) {
        return tarjeta.findElements(By.cssSelector(
                        "[data-testid*='pod-title'], a[href*='/articulo/']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .map(String::trim)
                .filter(nombre -> !nombre.isBlank())
                .findFirst()
                .orElse("");
    }

    private boolean tienePrecioCLP(WebElement tarjeta) {
        return tarjeta.findElements(By.cssSelector(
                        "[data-testid*='price'], [class*='price']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .anyMatch(texto -> texto.matches("(?s).*\\$\\s*\\d+(?:\\.\\d{3})*(?:,\\d{2})?.*"));
    }

    private WebElement obtenerEnlaceProducto(WebElement tarjeta) {
        return tarjeta.findElements(By.cssSelector("a[href*='/articulo/']")).stream()
                .filter(WebElement::isDisplayed)
                .filter(enlace -> {
                    String href = enlace.getAttribute("href");
                    return href != null && !href.isBlank();
                })
                .findFirst()
                .orElse(null);
    }

}
