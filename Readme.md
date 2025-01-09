# General Project Information
- ### General
   -   ```<modelVersion>4.0.0</modelVersion>``` - декларация версии дескриптора POM
   -   ```<packaging>jar</packaging>``` - во что будет билдиться проект jar, war, и т.д. а также от него зависит жизненный цикл приложения
   -   ```<name>CustomArchetypeMaven</name>``` - название модуля проекта
   -   ```<url>http://maven.apache.org</url>``` - url проекта
   - ```
         <contributors>
            <contributor>
               <email>some-email</email>
               <name>Valentin</name>
               <organization>Not found</organization>
            </contributor>
         </contributors> - кто участник
  - ```
      <licenses>
         <license>
            <name>No License</name>
            <comments>No comet</comments>
            <distribution>No distribution</distribution>
         </license>
      </licenses> - инфа по лицензии
- ### POM Relationships
   - ```<groupId>org.example</groupId>``` - groupId artifactId version - предствляют собой уникальный идентификатор проекта
     ```<artifactId>CustomArchetypeMaven</artifactId>```
     ```<version>1.0-SNAPSHOT</version>```
     - ```
        <dependencies>
           <dependency>
             <groupId>junit</groupId>
             <artifactId>junit</artifactId>
             <version>3.8.1</version> - groupId artifactId version уникальное имя зависимости 
             <>exclusions 
             <scope>test</scope> - значения на каком этапе понадобиться зависимость значения: 
                    complie - зависмость потребуется на этапе компиляции (jar от нее полностью зависим), 
                    provided - зависимость будет предоставлена кем то другим (например tomcat для jakarta.servlet-api), 
                    runtime - runtime  зависимость driver в jdbc который нужен только на этапе запросов, 
                    system - лежит на лоакльном компе(лучше не использовать), 
                    test - эта зависмость нужна только для выполнения наших тестов
           </dependency>
        </dependencies> - подтягиваемые зависимости (jar файлы)
- ### Build Environment
     - ```
        <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties> - глобальные переменные для POM
  - ### Build settings
    - ```<repoting></reporting>``` - внедрение плагинов и т.д. на этапе reporting
    - ```
       <build>
         <directory>out</directory> - указание декректории куда билдить файлы default папка target
       </build> - настройки стадии билда
- ### Команды для maven
    - `mvn compiler:compile` - запускает plugin: compiler с goal: compile, goal: help посмотреть все goal у плагина
    - `mvn help:effective-pom` - гененрит итоговую pom-ку, в которую включены итоговые плагины, super pom и сама pom
    - `mvn dependency:analyze` - анализирует зависимости, goal tree - строит дерево транзитивных зависимостей 
