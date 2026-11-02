@SODIMAC
@web @catalogo @critico
Feature: Validación visual y funcional del catálogo Navidad

  # Abrir Sodimac y navegar al catálogo desde el menú
  Background:
    Given que se abre el navegador y se navega a "https://www.sodimac.cl/sodimac-cl"

  # Buscar productos navideños
  @CP001
  Scenario: Validar elementos visibles del catálogo
    And el usuario hace clic en el Menú
    And hace click en submenú Navidad - Todo Navidad
    When finaliza la carga visual de la página
    Then debe visualizar el encabezado principal
    And debe visualizar el título de la categoría
    And debe visualizar el contenedor de productos
    And cada tarjeta de producto visible debe mostrar
      | objeto             |
      | Imagen             |
      | Nombre             |
      | Precio             |
      | Enlace de producto |
    And las imágenes deben mostrarse correctamente
    And los nombres no deben estar vacíos
    And los precios deben tener formato monetario CLP
    And ningún elemento debe superponerse
    And no deben existir tarjetas visualmente duplicadas
