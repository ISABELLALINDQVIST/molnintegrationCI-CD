# Maven Spring Boot Webservice med CI/CD Pipeline på AWS

## Inledning
Detta projekt är en Maven Spring Boot-applikation med CI/CD pipeline via GitHub Actions och AWS. 
Syftet är att automatisera bygg-, test- och deploy-processen.

## Funktionalitet
- Spring Boot webservice som hanterar HTTP-anrop.
- CI/CD-pipeline med GitHub Actions för bygg och test.
- Automatisk deployment till AWS vid varje push till GitHub.
- Enviroment, build och pipeline även i AWS

## Teknisk Specifikation
- **Tekniker**: Java, Maven, Spring Boot, GitHub Actions, AWS.
- **CI/CD Tools**: GitHub Actions och AWS.
- **Testning** : Github Actions och AWS samt Unittester på Controllerklasser,

## CI/CD Pipeline
### GitHub Actions Workflow:
- Bygger och testar applikationen.
- Deployment sker till AWS efter godkänd bygg och test.

### AWS Deployment Process:
- Applikationen hostas på AWS EC2.

## Endpoints (testning via Postman)
### Endpoints för AuthorController:
- GET http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/authors  
Hämtar en lista över alla författare.  
Svar: 200 OK med en lista av Author-objekt.

- GET http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/authors/{id}  
Hämtar en specifik författare baserat på deras ID.  
Svar: 200 OK samt uppvisar den sökta författaren

- POST http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/authors  
Skapar en ny författare.   
Payload: Body - JSON 
```{
  "name": "George Orwell",
  "age": 46
  }
```
Svar: 200 OK med det nyss skapade Author-objektet.

- PUT http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/authors/11  
Uppdaterar en befintlig författare baserat på deras ID (11 i detta fall).   
Payload: Body - JSON
``` {
  "name": "Updated Author",
  "age": 50
}

  ```
Svar: 200 OK med det uppdaterade Author-objektet.

- DELETE http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/authors/11  
Tar bort en författare baserat på deras ID (11 i detta fall).   
Payload: Body - JSON
```{
  "id": 11
  }
  ```
Svar: 200 OK med ett meddelande om framgång ("Författaren är nu raderad!").

### Endpoints för BooksController:
- GET http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/books  
Hämtar en lista över alla böcker.   
Svar: 200 OK med en lista av Books-objekt.

- GET http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/books/10  
Hämtar en specifik bok baserat på dess ID (10 i detta fall)  
Svar: 200 OK med den sökta boken

- POST http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/books  
Skapar en ny bok.   
Payload: Body - JSON
 ``` {
  "title": "1984",
  "isbn": "978-0451524935"
  }
  ```
Svar: 200 OK med den nyss skapade boken.

- PATCH http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/books/16  
Uppdaterar en befintlig bok baserat på dess ID (16 i detta fall).   
Payload: Body - JSON
```{
  "title": "1985",
  "isbn": "978-0451524935"
  }
  ```
Svar: 200 OK med den uppdaterade boken.

- DELETE http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com/books/16  
Tar bort en bok baserat på dess ID (16 i detta fall).   
Svar: 200 OK med ett meddelande om framgång ("Boken är nu raderad!").

## Beskrivning av AWS Pipeline process  
AWS CodePipeline är en kontinuerlig leveransservice vi använde oss utav som automatiserar bygg, test, och deployprocessen.  
Vi har använt oss av detta för att få en effektiv och automatiserad metod till bygg, test och deploy.  
Detta säkerställer att applikationen alltid är stabil för produktion.

### Kodkällan  
Pipelinen börjar med att definera källan för koden, i mitt fall är detta mitt github repo som jag kopplat till min AWS.  
När det görs ändringar i koden som pushas upp så triggas pipelinen per automatik. 

### Buildprocess  
Jag har använt mig av AWS Codebuild som är en tjänst som automatiskt bygger och testar koden.  
Jag har angett byggspecen i min Codebuild, att projektet skall byggas, installerade beroenden och göra rent efter sig.  
Under byggsteget körs även automatiskerade tester för att säkerställa att min kod fungerar som den ska.  
Om bygget misslyckas, eller om testerna misslyckas så stoppas pipelinen och ingen deploy kommer göras.  

### Testning  
Jag har inkluderat tester i byggspecifikationen, som gör att man här får se tydligt om något test inte går igenom  
vilket underlättar fixadet av dessa.  

### Deploy  
Efter att bygg och teststeget har lyckats så deployas applikationen till AWS Elastic Beanstalk.  
Den hanterar distrubution och konfigurationen av själva miljön där applikationen körs.
