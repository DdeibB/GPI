## git clone https://github.com/k-tamura/easybuggy.git
cd easybuggy
mvn compile
## ejecutar el analisis de codigo
~/Escritorio/pmd-bin-6.45.0/bin/run.sh pmd -d ./ -R rulesets/java/quickstart.xml -f text
