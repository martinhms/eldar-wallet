# EldarWallet

EldarWallet es una aplicación de billetera virtual para dispositivos móviles que permite a los usuarios realizar múltiples operaciones financieras.## Descripción

Esta aplicación permitirá a los usuarios:

* Consultar su saldo proveniente de transferencias.
* Agregar y administrar tarjetas de pago.
* Realizar pagos con QR.
* Simular pagos con NFC.

## Pantallas

La aplicación contará con las siguientes pantallas:

* **Login:** Pantalla de inicio de sesión para autenticar a los usuarios.
* **Pantalla principal:** Muestra el saldo del usuario, un listado de tarjetas asociadas y botones para acceder a las demás funcionalidades.
* **Agregar una nueva tarjeta:** Permite a los usuarios agregar nuevas tarjetas de pago a su billetera.
* **Pago con QR:** Genera un código QR con el nombre y apellido del usuario para realizar pagos.
* **Generar un pago:** Permite simular un pago seleccionando la tarjeta deseada y asumiendo que la compra se realiza a través de NFC.

## Funcionalidades

* **Almacenamiento dedatos del usuario:** Se almacenará el nombre y apellido del usuario para verificar la propiedad de las tarjetas.
* **Detección de marca de tarjeta:** La aplicación detectará automáticamente la marca de la tarjeta (Visa, Mastercard, American Express) basándose en el primer dígito del número de tarjeta.
* **Encriptación de datos:** La información de las tarjetas se almacenará de forma encriptada en la base de datos interna de la aplicación.

## API

Se utilizará la siguiente API para generar códigos QR:

* [QRCode68](https://rapidapi.com/zingzy/api/qrcode68)

## Consideraciones

* La implementación de la funcionalidad NFC no es requerida para este desafío.

## Tecnologías

* Se utilizará el lenguaje de programación Kotlin.
* Se utilizará la librería de View y ViewGroup para el desarrollo de la interfaz de usuario.
* Se utilizará Room para la persistencia de datos.
* DI con Dagger-Hilt
* Se utilizará una librería de encriptación para el almacenamiento seguro de la información de las tarjetas.
* Se utilizará Retrofit para la integración con la API de generación de códigos QR.

## Próximos pasos

* Diseño de la interfaz de usuario.
  *Implementación de la lógica de la aplicación.
* Integración con la API de códigos QR.
* Implementación del almacenamiento encriptado de datos.
* Pruebas y despliegue.