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
         <parent>
           <groupId>org.example</groupId> - указывается в children module, наследуемся от этого модуля
           <artifactId>CustomArchetypeMaven</artifactId>
           <version>1.0-SNAPSHOT</version>
         </parent>
        - ```
            <modules> - указывается в parent module, созависимые модули
              <module>childrenProject</module>         
            </modules>
        - ```
          <dependencyManagement> - указывается в родительском pom, в дочернем нужно только укзать groupId и artifactId для dependency
           <dependencies>
              <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>3.8.1</version> - groupId artifactId version уникальное имя зависимости 
                <exclusions> - исключение транзитивных зависмостей, чтобы она подтянулась в другой
                   <exclusion>
                     <groupId>org.example</groupId>
                     <artifactId>CustomArchetypeMaven</artifactId>
                   </exclusion>
                </exclusions> 
                <optional>true</optional> - если true они не установятся, но тот кто рабоатет с pom явно должен усановить у себя одну помеченных optional
                <scope>test</scope> - значения на каком этапе понадобиться зависимость значения: 
                       complie - зависмость потребуется на этапе компиляции (jar от нее полностью зависим), 
                       provided - зависимость будет предоставлена кем то другим (например tomcat для jakarta.servlet-api), 
                       runtime - runtime  зависимость driver в jdbc который нужен только на этапе запросов, 
                       system - лежит на лоакльном компе(лучше не использовать), 
                       test - эта зависмость нужна только для выполнения наших тестов
              </dependency>
              <dependency> - зависимость для добавления api создания плагина
                  <groupId>org.apache.maven</groupId>
                  <artifactId>maven-plugin-api</artifactId>
                  <version>3.9.8</version>
                  <scope>provided</scope>
              </dependency>
              <dependency>  - зависимость для добавления Mojo анатаций для плагина
                  <groupId>org.apache.maven.plugin-tools</groupId>
                  <artifactId>maven-plugin-annotations</artifactId>
                  <version>3.13.1</version>
                  <scope>provided</scope>
              </dependency>
           </dependencies> - подтягиваемые зависимости (jar файлы)
          </dependencyManagement>
- ### Build Environment
     - ```
        <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties> - глобальные переменные для POM
  - ### Build settings
    - ```<repoting></reporting>``` - внедрение плагинов и т.д. на этапе reporting
      - ```
         <build>
          <finalName>ROOT</finalName> - определение именования архива в packaging (не использовать для multi module)
           <directory>out</directory> - указание декректории куда билдить файлы default папка target
            <pluginManagement> - указывается в родительском pom, в дочернем нужно только укзать groupId и artifactId для plugin
             <plugins> - переопределение плагина для фазы
              <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-compiler-plugin</artifactId>
                 <version>3.13.0</version>
                <configuration> - конфигурация плагина
                  <source>23</source>
                  <target>23</target>
                </configuration>
                <executions> - привязка goal к lifecycle maven
                  <execution>
                    <id>custom-execution</id> - кастомный id вызова
                    <goals>
                      <goal>compile</goal> - привязываемая гола
                    </goals>
                    <phase>validate</phase> - фаза к оторой идет приавязка
                  </execution>
                </executions>
              </plugin>
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.3.1</version>
                <executions>
                  <execution>
                    <id>custom-copy-resources</id>
                    <goals>
                      <goal>copy-resources</goal>
                    </goals>
                    <phase>validate</phase>
                    <configuration>
                      <outputDirectory>${project.basedir}/target</outputDirectory>
                      <resources>
                        <resource>
                          <directory>${project.basedir}/src/imageFolder</directory>
                        </resource>
                        <resource>
                          <directory>${project.basedir}/src/main/resources</directory>
                          <filtering>true</filtering> - обрабатывает и подставляет значения в ресурсы из project.*, settings.* (mvn), propertiies.*, env.*, system.getproperty
                        </resource>
                      </resources>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
              <plugin> - создание одной jar-ки с  распакованными внутри нее зависимостями  (создается manifest в out)
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId> 
                <version>3.7.1</version>
                <executions>
                  <execution>
                    <id>single-jar</id> - наименование при сборки мавен
                    <goals>
                      <goal>single</goal>
                    </goals>
                    <phase>package</phase>
                    <configuration> - префикс для создаваемой jar
                      <descriptorRefs>jar-with-dependencies</descriptorRefs>
                      <archive>
                        <manifest> - добавление в манивест main class
                          <mainClass>org.example.App</mainClass>
                        </manifest>
                      </archive>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
              <plugin> - создание одной jar-ки с подгруженными зависимостями вотдельных jar (создается manifest  в out)
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId> 
                <version>3.2.0</version>
                <executions>
                  <execution>
                    <id>create-lib</id>
                    <goals>
                      <goal>copy-dependencies</goal>
                    </goals>
                    <phase>prepare-package</phase>
                    <configuration> - директория куда класть зависимости
                      <outputDirectory>${project.build.directory}/lib</outputDirectory>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
              <plugin> - кастомное создние плагина
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <version>3.13.1</version>
                <executions>
                  <execution>
                    <id>generated-helpmojo</id>
                    <goals>
                      <goal>helpmojo</goal>
                    </goals>
                  </execution>
                </executions>
                <configuration>
                  <goalPrefix>my-plugin-prefix</goalPrefix>
                </configuration>
              </plugin>
              <plugin>-  подключение созданного плагина
                <groupId>org.example</groupId>
                <artifactId>CustomPlugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>
                                CustomPlugin
                            </goal>
                        </goals>
                        <phase>compile</phase>
                    </execution>
                </executions>
            </plugin>
           </plugins> 
          </pluginManagement>
         <profiles> - добавление профиля в maven
          <profile>
            <id>dev</id>  - наименование профиля в maven
            <activation> - условия активации профиля 
              <activeByDefault>false</activeByDefault> - активация по дефолту
              <file> - активация по наличию файла
                <exists>out</exists>
              </file>
              <jdk>21</jdk> - активация версии jdk
              <os>
                <family>windows</family>  - активация версии OS
              </os>
              <property> - активация по ппередаваемому свойству и значению -Dtets=test
                <name>test</name>
                <value>test</value>
              </property>
            </activation>
            <properties> - переопределяемые значения при автивации профиля
              <maven.compiler.source>22</maven.compiler.source>
              <maven.compiler.target>22</maven.compiler.target>
            </properties>
            <build> - переопределяемые значения при автивации профиля
              <directory>out1</directory>
            </build>
          </profile>
        </profiles>
         <distributionManagement> - настройка деплоя в nexus(артифактори)
            <snapshotRepository> - для версии с snapshot <version>1.0-SNAPSHOT</version>
              <id>nexusSnapshot</id> - id для установки соответствия настроек в ~/.m2/settigs.xml (не забыть прописать тэг server там)
              <url>http://localhost:9000/repository/maven-snapshots/</url> - url в репозитория nexus
            </snapshotRepository>
            <repository> - для версии без snapshot <version>1.0</version>
              <id>nexusReleases</id>
              <url>http://localhost:9000/repository/maven-releases/</url>
            </repository>
         </distributionManagement>
        </build> - настройки стадии билда
- ### Команды для maven
    - `mvn compiler:compile` - запускает plugin: compiler с goal: compile, goal: help посмотреть все goal у плагина
    - `mvn help:effective-pom` - гененрит итоговую pom-ку, в которую включены итоговые плагины, super pom и сама pom
    - `mvn dependency:analyze` - анализирует зависимости, goal tree - строит дерево транзитивных зависимостей 
    - `mvn dependency:tree -Dverbose` -  goal tree - строит дерево транзитивных зависимостей, -Dverbose - более полная инфа 
- ### Фазы maven lifecycle
![lifecycle](CustomArchetypeModule/ChildrenArchetypeModule/src/main/resources/lifecycle.png)
- `mvn clean` - запускает фазу clean, удаляет папку target 
- `mvn validate` - валидирует проект, проверка в зависмостей 
- `mvn compile` - компилирует проект, обработка исходников и гененрация ресурсов - берет исходники из main src, обрабатывает и кладет их в target, вызываются предыдущие фазы 
- `mvn test` - запускет тесты в проекте
- `mvn package` - запаковывает в указанный архив (jar не включается в себя зависмости), формат определяется в  `<packaging>jar</packaging>`
- `mvn verify` - проеряет что все успешно собралось
- `mvn install` - кладет артифакты в m2 repository - после их можно подключать в другие проекты через dependencies
- `mvn deploy` - кладет артифакты из m2 repository в nexus

- `mvn compile -Pdev` - кладет артифакты из m2 repository в nexus
