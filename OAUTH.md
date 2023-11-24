##  Open Authorization (OAuth)

Es un framework de autorización, es abierto, esta construido en estandares IETF y licenciado bajo Open Web Foundation.

Es un protocolo de delegacion:

Permite que alguien que controla un recurso permita a una aplicacion de software pueda acceder a ese recurso en su propio nombre sin pasar por ello.

Con la ayuda de OAuth los usuarios pueden authorizar a third part applications (aplicaciones de terceros) a acceder a sus datos o ejecutar determinadas operaciones 
sin necesidad de proporcionar usuario y contraseña.

## Flujo de trabajo con OAuth:

1. Una aplicacion solicita autenticacion
2. Se realiza login mediante Google
3. La aplicacion se comunica con Google donde se utilizan las credenciales de Google sin que la aplicacion pueda verlas
4. El Servidor de Google pregunta ala usuario si desea conceder X permisos
5. El usuario acepta los permisos
6. Google genera un token de acceso como respuesta
7. La aplicacion utiliza ese token

## Ascenarios para aplicar OAuth

1. Autenticacion HTTP en la que no se quiere utilizar usuario y contraseña todo el tiempo
2. Multiples aplicaciones dentro de una misma empresa y en consecuencia multiples cuentas con el mismo usuario y contraseña
3. Arquitecturas de microservicios
4. Interaccion de aplicaciones de terceros


## Proveedores

Google, Github, Facebook, Okta (Empresa de administración de acceso e identidad)

## OAuth en Spring Security

Inicialmente habia un proyecto llamado Spring Security OAuth

en 2018 se sobreescribe para hacerlo mas eficiente, con un codigo base mas sencillo

ha llegado al final de su vida útil el antiguo OAuth (https://spring.io/projects/spring-security-oauth) y ahora OAuth2 esta integrado sobre el propio Spring Security

Ver ejemplos de aplicaciones: (https://github.com/spring-guides/tut-spring-boot-oauth2)

## OAuth2 Incluye

* Client Support: Permite que tu aplicación actúe como cliente en un flujo OAuth 2.0, autenticándose en un proveedor de identidad y obteniendo tokens de acceso.
* Core:  Proporciona las clases base y la infraestructura necesaria para admitir OAuth 2.0 en Spring Security.

(Dependencias que no incluye)
* Resource Server: Proporciona soporte para implementar un servidor de recursos en OAuth 2.0, protegiendo tus propios recursos y validando tokens de acceso entrantes.
* Authorization Server: Permite implementar un servidor de autorización personalizado en OAuth 2.0, emitiendo tokens de acceso y gestionando flujos de autorización.
* Okta: Proporciona soporte específico para la integración con Okta en flujos OAuth 2.0. Ayuda a configurar la autenticación, la obtención de tokens y la integración con los servicios de Okta.

## Softare de gestion Keycloack

Se encarga de la autenticacion, permite el inicio de sesión único con gestión de acceso e identidad dirigido a aplicaciones y servicios modernos.

Genera una web desde la cual prepara toda la autenticacion de manera grafica
(https://www.keycloak.org/)

## Flujos de accion OAuth2

* Authorization code
* Implicit
* Resource Owner password credentials
* Refresh Token

## OpenID Connect

* OpenID Connect --> proporcion Authentication
* OAuth2 --> proporciona Authorization


## Conclusion

OAuth es un protocolo de autorización que actúa como un servidor externo encargado de gestionar la autenticación y la autorización en servicios sin necesidad de compartir directamente las credenciales del usuario (usuario y contraseña) con las aplicaciones externas. Este enfoque permite que las aplicaciones obtengan acceso limitado a recursos o realizar operaciones específicas en nombre del usuario, manteniendo la seguridad y sin comprometer las credenciales de autenticación del usuario. 