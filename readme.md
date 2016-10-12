# ecommerce-example

## A basic ecommerce example built using [Struts 2](http://struts.apache.org/) ,[Spring](https://spring.io/), [Tiles](https://tiles.apache.org/), and [MySql](http://www.mysql.com/).
 
 
 
 **How to run**:
  1. Download repository
  2. Update application.properties with database user and password
  3. Update pom.xml with database user and password  
  4. Execute mvn tomcat7:run
  5. Load localhost:8080/Ecommerce on your favorite browser
  
 Test datasource configuration will be in /src/test/resources/testDbUnitContext.xml   
 If you don't want to use preset products and users add -Dbuild.preset.skip=true argument
  
