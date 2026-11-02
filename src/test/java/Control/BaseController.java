package Control;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Constant.Constant;

public class BaseController {
    private final WebDriver driver;

    public BaseController() {
        this.driver = DriverContext.getDriver();
        if (this.driver == null) {
            System.out.println("WebDriver no está inicializado!");
        }
    }

    protected void initPage() {
        if (this.driver != null) {
            PageFactory.initElements(new AjaxElementLocatorFactory(this.driver, Constant.TIME_RESPONSE), this);
        }
    }

    public boolean visualizarElemento(WebElement elementoWeb, int tiempoEspera) {
        try {
            WebDriverWait wait2 = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(tiempoEspera));
            wait2.until(ExpectedConditions.visibilityOf(elementoWeb));
            System.out.println("Es visible el elemento web " + elementoWeb.getText());
            return true;
        } catch (Exception e) {
            System.out.println("No es visible el elemento web" + elementoWeb);
            return false;
        }
    }
}