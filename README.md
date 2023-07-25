# DatabseAPI
DatabaseAPI is a Minecraft Spigot Plugin tool which simplifies and speeds up the process of connecting and getting data from a database.
# Index
-  [How do i install this library?](#how-to-install)
-  [How can i connect to a database and get some data?](#how-do-i-use-the-library)

## How to Install
You can use this maven repository.

Add tese lines to youre pom.xml file.

**Repository**:
My github packages repository.

```xml
        <repository>
            <id>MinecraftPluginAPI</id>
            <url>https://maven.pkg.github.com/DavidOut03/DatabaseAPI</url>
        </repository>
```



**Dependency (type latest version in for `$version`)**
```xml
        <dependency>
            <groupId>com.davidout.api</groupId>
            <artifactId>database-api</artifactId>
            <version>$version</version>
        </dependency>
```



## How do i use the library?

```java

public class Main {




    public static void main(String[] args) throws Exception {
        String databaseName = "your_database_name";
        String host = "localhost";
        int mongoPort = 27017;
        int mysqlPort = 3306;
        String username = "root";
        String password = "admin";


        MySQLDatabase database = (MySQLDatabase) DatabaseManager.getDatabase(DatabaseManager.DatabaseType.MYSQL, databaseName, host, mysqlPort, username, password);
        database.connect(aBoolean -> {
            System.out.println("Connected to mysql database.");
            database.query("SELECT * FROM your_database_name.your_table_name;", table -> {
                System.out.println("These are the mysql values;");
                System.out.println(table.toString());
            }, Throwable::printStackTrace);
        });


        MongoDatabase database1 = (MongoDatabase) DatabaseManager.getDatabase(DatabaseManager.DatabaseType.MongoDB, databaseName, host, mongoPort, null, null);
        database1.connect(aBoolean -> {
            if (aBoolean) {
                System.out.println("Connected to mongo database.");

                database1.query("your_collection_name", "", table -> {
                    System.out.println("These are the mongo values;");
                    System.out.println(table.toString());
                }, throwable -> {
                    System.out.println("ERROR");
                });

            }
        }, Throwable::printStackTrace);

    }
}

```



See [LICENSE.md](LICENSE.md) for license information.
