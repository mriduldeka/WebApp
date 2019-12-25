Rest Api's info:

This application is built on Spring boot, Hibernate, PostgresSQL, Maven. All API's are tested running on Tomcat Server.

Below Http URL's can be used:

http://localhost:8080:/User/signUp  is a public URL used for signing up for user. No token will be issued for this.Request body can be reffered from screenshots given in mail.

http://localhost:8080:/login should be used to login.This URL is not explicitly mentioned in controller.Request will be automatically submitted for this URL to JWTAuthenticationFilter.java.
After credentials are authenticated, JWT token will be issued on authorization. Token can be obtained from Response.
e.g.) Request Body:{
       username:root2,
	   password:admin2
	   }
	   
All other API's defined in VmUserController.java class can be accesed through http://localhost:portNo:/(URL in @PostMapping). For each API valid token has to be issued in Authorization header, if using
postman select OAuth2.0. If token is invalid, then response status is forbidden.
