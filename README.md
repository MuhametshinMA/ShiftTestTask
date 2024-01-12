### Инструкция по запуску

#### Запуск программы
##### Запуск программы осуществляется с помощью системы сборки Maven, из директории где расположен pom.xml
- mvn clean package
- java -jar target/ShiftTestTask-1.0-SNAPSHOT.jar -f -a -p sample- -o /some/path in1.txt in2.txt

#### Версии
##### При написании программы были использованы следующие версии компонентов программ, а так же библиотеки.

- Java 18;
- Maven 3.6.3;
##### Библиотеки
- junit-jupiter-engine 5.8.0-M1 (https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine/5.10.0)

- lombok 1.18.20 (https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.20)
