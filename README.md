# Maven Spring Boot Webservice with CI/CD Pipeline on AWS

## Inledning
Detta projekt är en Maven Spring Boot-applikation med CI/CD pipeline via GitHub Actions och AWS. 
Syftet är att automatisera bygg-, test- och deploy-processen.

## Funktionalitet
- Spring Boot webservice som hanterar HTTP-anrop och svarar med ett meddelande.
- CI/CD-pipeline med GitHub Actions för bygg och test.
- Automatisk deployment till AWS vid varje push till GitHub.

## Teknisk Specifikation
- **Tekniker**: Java, Maven, Spring Boot, GitHub Actions, AWS.
- **CI/CD Tools**: GitHub Actions och AWS.

## CI/CD Pipeline
### GitHub Actions Workflow:
- Bygger och testar applikationen.
- Deployment sker till AWS efter godkänd bygg och test.

### AWS Deployment Process:
- Applikationen hostas på AWS EC2.

## Installation & Körning
1. Klona repository:
   ```bash
   git clone https://github.com/ISABELLALINDQVIST/molnintegrationCI-CD.git
   cd molnintegrationCI-CD
