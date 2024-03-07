# README

Utilities to convert to/from pdf files.

## Prerequirements

- Java 17
- Maven

## Build

```
mvn clean package
```

## Run

There are several way to run the application.

Compile and run with maven

```
mvn clean compile exec:java "-Dexec.args=$arg1 $arg2 ..." "-Dexec.mainClass=$mainClass"
```

Run from the target folder

```
java -cp target/doc-magic-jar-with-dependencies.jar $mainClass $arg1 $arg2 ...
```

Run the distribution version

```
java -cp doc-magic.jar $mainClass $arg1 $arg2 ...
```

### Pdf2Pngs

```
java -cp doc-magic.jar it.vitalegi.docsmagic.App pdf2png "src/test/resources/Lorem ipsum.pdf"
```

### Pngs2Pdf

```
java -cp doc-magic.jar it.vitalegi.docsmagic.App png2pdf src/test/resources "Lorem ipsum Sample.pdf"
```


## Contributions

Thanks, but no.

## Bugs

Keep them. 

## Support

You are on your own.
