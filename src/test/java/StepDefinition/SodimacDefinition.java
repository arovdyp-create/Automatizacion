package StepDefinition;

import ObjectPage.SodimacPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

public class SodimacDefinition {
    private SodimacPage sodimacPage;

    @And("el usuario hace clic en el Menú")
    public void desplegarMenu() {
        sodimacPage = new SodimacPage();
        sodimacPage.desplegarMenu();
    }

    @And("hace click en submenú Navidad - Todo Navidad")
    public void moduloNavidad() throws InterruptedException {
        sodimacPage.moduloNavidad();
        Thread.sleep(2000);
        sodimacPage.subMenuNavidad();
        Thread.sleep(2000);
    }

    //  Primer caso
   @When("finaliza la carga visual de la página")
   public void cargaPagina() {
       if (sodimacPage == null) {
           sodimacPage = new SodimacPage();
       }
       sodimacPage.verPagina();
   }

   @Then("debe visualizar el encabezado principal")
    public void verTitulo(){
         sodimacPage.verTitulo();
   }

   @And("debe visualizar el título de la categoría")
       public void categoria(){
        sodimacPage.tituloCategoria();
       }

    @And("debe visualizar el contenedor de productos")
    public void verProductos(){
        sodimacPage.verContenedor();
    }

    @And("cada tarjeta de producto visible debe mostrar")
    public void cadaTarjetaDebeMostrar(DataTable tabla) {
        List<String> campos = tabla.asLists(String.class).stream()
                .skip(1)
                .map(fila -> fila.get(0))
                .toList();
        sodimacPage.validarTarjetasProducto(campos);
    }

    @And("las imágenes deben mostrarse correctamente")
    public void validarImagenes() {
        sodimacPage.validarImagenesProducto();
    }

    @And("los nombres no deben estar vacíos")
    public void validarNombres() {
        sodimacPage.validarNombresProducto();
    }

    @And("los precios deben tener formato monetario CLP")
    public void validarPreciosCLP() {
        sodimacPage.validarPreciosCLP();
    }

    @And("ningún elemento debe superponerse")
    public void validarSuperposiciones() {
        sodimacPage.validarTarjetasSinSuperposicion();
    }

    @And("no deben existir tarjetas visualmente duplicadas")
    public void validarDuplicados() {
        sodimacPage.validarTarjetasSinDuplicados();
    }

}
