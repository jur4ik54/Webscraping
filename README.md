Scraping web App

PORTALNAME:
<br>freelancermap
<br>computerfutures
<br>etengo
<br>solcom
<br>gulp
<br>all
<br>API:
<br>your-domain.de/fetch/Portalname

<br>How to set up:
<br>change java/main/resources/applicationEXAMPLE.properties to  application.properties
<br>mvn clean install 
<br>mvn spring-boot:run
<br>oder mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085
<br><br> unter windows hat bei mir gereicht einfach chrome installiert zu haben (Version 114.0.5735.134 (Offizieller Build) (64-Bit))
<br><br>ansonsten install driver https://tecadmin.net/setup-selenium-chromedriver-on-ubuntu/
