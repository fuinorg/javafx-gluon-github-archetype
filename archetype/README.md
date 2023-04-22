# JavaFX Gluon Github Archetype
Archetype that creates a new JavaFX Gluon Github Maven project.

## Generate

### Mandatory Parameters
```
mvn archetype:generate \
     -DarchetypeGroupId=org.fuin.archetypes \
     -DarchetypeArtifactId=javafx-gluon-github-archetype \
     -DarchetypeVersion=0.1.0-SNAPSHOT
```

### All Parameters
```
mvn archetype:generate \
     -DarchetypeGroupId=org.fuin.archetypes \
     -DarchetypeArtifactId=javafx-gluon-github-archetype \
     -DarchetypeVersion=0.1.0-SNAPSHOT \
     -DgroupId="com.example" \
     -DartifactId="abc-myapp" \
     -Dversion="0.1.0-SNAPSHOT" \
     -DpkgName="com.example.abc" \
     -DappName="AbcApp" \
     -DappDescription="My cool ABC application" \
     -Dcompany="My Company Ltd."
     
```

### Explanation

| Variable       | Default                      | Description                           |
|:---------------|:-----------------------------|:--------------------------------------|
| groupId        | com.example                  | Your app Group ID                     |
| artifactId     | myapp-archetype              | Your app Artifact ID                  |
| version        | 0.1.0-SNAPSHOT               | Your app version                      |
| pkgName        | com.example.myapp            | The base package of your app          |
| appName        | MyApp                        | Camelcase name of your app            |
| appDescription | My cool application          | One-liner describing your app         |
| company        | My Company Ltd.              | The name of your company/organization |


### Tips
In case you're developing a new version of this archetype and you want to build and test it locally, you need to tell Maven about it:
```
mvn archetype:generate \
     -DarchetypeGroupId=org.fuin.archetypes \
     -DarchetypeArtifactId=javafx-gluon-github-archetype \
     -DarchetypeVersion=0.1.0-SNAPSHOT \
     ...
     -DarchetypeCatalog=local
```
The `-DarchetypeCatalog=local` enables Maven to use the locally installed Archetype.
