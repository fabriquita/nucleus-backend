NUCLEUS BACKEND
===============

Run
---

```sh
gradle bootRun
```

Populate Database
-----------------

```sh
gradle bootRun -Ppopdb
```

Generate Eclipse Project
------------------------

Generate:
```sh
gradle eclipse
```

Regenerate:
```sh
gradle cleanEclipse eclipse
```

Execute Unit Tests
------------------

```sh
gradle test
```

Generate WAR file
-----------------

```sh
gradle assemble
```

Generate WAR file and Execute Unit Tests
----------------------------------------

```sh
gradle build
```

