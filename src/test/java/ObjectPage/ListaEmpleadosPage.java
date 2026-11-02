package ObjectPage;

import Control.BaseController;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListaEmpleadosPage extends BaseController {
   //private WebDriver driver;

    public ListaEmpleadosPage(){
        initPage();
    }

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")
    private WebElement nombreUsuario;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input")
    private WebElement claveUsuario;

    @FindBy(css = ".orangehrm-login-button")
    private WebElement botonLogin;

   public void escribirUsersname(String usuario) {
        try {
            if (visualizarElemento(this.nombreUsuario, 10)) {
                this.nombreUsuario.clear();
                this.nombreUsuario.sendKeys(usuario);
            } else{
                throw new AssertionError("No se encuentra el campo Nombre de Usuario en la página");
            }
        } catch (Exception e) {
            throw new AssertionError("Error al escribir en el campo de usuario: " + e.getMessage(), e);
        }
    }

    public void escribirClaveUsuario(String contrasena) {
        try {
            if (visualizarElemento(this.claveUsuario, 10)) {
                this.claveUsuario.clear();
                this.claveUsuario.sendKeys(contrasena);
            } else {
                throw new AssertionError("No se encuentra el campo Password en la página");
            }
        } catch (Exception e) {
            throw new AssertionError("Error al escribir en el campo de password: " + e.getMessage(), e);
        }
    }

    public void bttLogin() throws InterruptedException {
        this.botonLogin.click();
        Thread.sleep(5000);
    }
}