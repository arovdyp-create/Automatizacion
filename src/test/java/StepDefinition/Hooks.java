package StepDefinition;

import Constant.Constant;
import Control.DriverContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class Hooks {
    private Scenario scenario;
    private static final String tomarCapturaPantalla;

    static{
        tomarCapturaPantalla = System.getProperty("evidence","fullEvidence");
    }

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
        Constant.scenarioStep = scenario;
        Constant.build_name = "Nombre de Proyecto";
        DriverContext.setDetachBrowser(scenario.getSourceTagNames().contains("@SODIMAC"));
    }

    @After
    public void tearDown(){
        if (!scenario.getSourceTagNames().contains("@SODIMAC")) {
            DriverContext.quitDriver();
        }
    }

    public void generarEvidencia(String imageRefName){
        WebDriver driver = DriverContext.getDriver();
        if (driver == null) {
            this.scenario.log("No se adjuntó captura: el WebDriver ya no está disponible.");
            return;
        }
        try {
            byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            this.scenario.attach(screenShot,"image/png", imageRefName);
        } catch (WebDriverException e) {
            this.scenario.log("No se pudo adjuntar la captura porque Chrome perdió la sesión: "
                    + e.getMessage());
        }
    }

    @AfterStep
    public void capturaEvidencia(){
        if(this.scenario.isFailed()){
            generarEvidencia("[FAIL] Step ScreenShots");
        }else if(Hooks.tomarCapturaPantalla.equalsIgnoreCase("fullEvidence")){
            generarEvidencia("[SUCCESS] Step ScreenShots");
        }
    }
}
