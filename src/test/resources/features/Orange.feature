@PIM
Feature: Busqueda y filtros de la lista de empleados

  # Iniciar sesión en OrangeHRM y acceder al módulo PIM
Background:Given que se abre el navegador y se navega a "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"

And que el usuario inicia sesión en OrangeHRM con el usuario "Admin" y la contraseña "admin123"
And el usuario hace clic en el módulo PIM

  # Buscar un empleado por nombre
@PIM @TC-001
Scenario Outline: Buscar empleado por nombre
When el usuario ingresa "<employeeName>" en el campo Nombre
And hace clic en boton Buscar
Then se deben mostrar los resultados de la búsqueda "<expectedName>"
#And cada resultado debe contener el nombre "<expectedName>"
Examples:
| employeeName | expectedName |
| Amelia       | Amelia       |

  # Buscar un empleado por Id
@PIM @TC-002
Scenario Outline: Buscar empleado por Id
When el usuario ingresa "<employeeId>" en el campo Id
And hace click en Buscar
Then se deben mostrar los resultados de la búsqueda "<expectedRecords>"
    Examples:
      | employeeId | expectedRecords  |
      | 01715      | 1                |
      | 99999      | 0                |

    # Buscar un empleado por status
@PIM @TC-003
  Scenario Outline: Buscar empleado por status estado
    When el usuario se posiciona en filtro
    And selecciona un "<status>"
    And click en Buscar
    Then se debe mostrar los resultados según el "<status>"
    Examples:
      | status                |
      | Freelance             |
      | Full-Time Contract    |
      | Full-Time Permanent   |
      | Full-Time Probation   |
      | Part-Time Contract    |

   # Buscar un empleado según filtro
@PIM @TC-004
  Scenario Outline: Search employees using include filter
    When el usuario se posiciona en opción Include
    And selecciona un nuevo "<includeOption>"
    And clicks en buscar
    Then se deben mostrar los resultados de "<includeOption>"
    Examples:
      | includeOption              |
      | Current Employees Only     |
      | Current and Past Employees |
      | Past Employees Only        |

  #Buscar por todos los filtros
@PIM @TC-005
Scenario Outline: Buscar empleados utilizando todos los filtros PIM disponibles
  When el usuario ingresa con el nombre empleado "<employeeName>"
  And el usuario ingresa el Id "<employeeId>"
  And el usuario selecciona un "<status>"
  And el usuario selecciona la opcion "<includeOption>"
  And el usuario selecciona el nombre del trabajo "<jobTitle>"
  And el usuario selecciona una sub Unidad "<subUnit>"
  And el usuario selecciona "<supervisor>"
  And realiza clic en buscar
  Then cada resultado debe mostrar los criterios seleccionados "<employeeName>" "<employeeId>" "<status>" "<includeOption>" "<jobTitle>" "<subUnit>" "<supervisor>"
  Examples:
    | employeeName | employeeId | status                | includeOption              | jobTitle                 | subUnit      | supervisor     |
    | Amelia      | 01715     | Full-Time Permanent   | Current Employees Only     | Account Assistant        | Administration | John  Doe  |
    | James       | 0365      | Full-Time Contract    | Current and Past Employees | Database Administrator   | Engineering  | Peter Mac Anderson |
    | Linda       | 0531      | Part-Time Contract    | Past Employees Only        | Automaton Tester        | Development  | Rebecca Harmony   |