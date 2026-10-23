package ObjectPage;

import Control.BaseController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.fail;

public class LoginPage extends BaseController {

    private WebDriver driver;

    @FindBy(id = "username")
    private WebElement nombreUsuario;

    @FindBy(id = "password")
    private WebElement claveUsuario;

    @FindBy(id = "submit")
    private WebElement botonLogin;

    @FindBy(className = "post-title")
    private WebElement mensajeLogin;


    public void escribirNombreUsuario(String nUsuario) {
        this.nombreUsuario.clear();
        this.nombreUsuario.sendKeys(nUsuario);
    }

    public void escribirClave(String pass) {
        this.claveUsuario.clear();
        this.claveUsuario.sendKeys(pass);
    }

    public void clickBtnSubmit() {
        this.botonLogin.click();
    }

    public void validarIngresoOk() {
        this.mensajeLogin.getText().equals("Logged In Successfully");
    }
}
