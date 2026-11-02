package StepDefinition;

import Constant.Navegador;
import Control.DriverContext;
import Control.NavSelector;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavDefinition {

    @Given("que se abre el navegador y se navega a {string}")
    public void abrirNavegador(String url) {

        String nav = System.getProperty("nav", "default");
        Navegador navegador = NavSelector.seleccionNavegador(nav);
        DriverContext.setUp(navegador, url);
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlToBe(url));
        wait.until(driver -> "complete".equals(
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.readyState")));
        String urlActual = DriverContext.getDriver().getCurrentUrl();
        System.out.println("URL Actual: "+ urlActual);
        Assert.assertEquals(urlActual,url);

    }
}
