## git clone https://github.com/sakaiproject/sakai.git
cd sakai/kernel
mvn install -e
## ejecutar empaquetado
mvn package
## ejecutar el analisis de codigo
~/Escritorio/pmd-bin-6.45.0/bin/run.sh pmd -d ./ -R rulesets/java/quickstart.xml -f text
## ejecutar las pruebas de test
mvn test
