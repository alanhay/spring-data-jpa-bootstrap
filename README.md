spring-data-jpa-bootstrap
=========================

Use to Bootstrap a New Spring Data JPA Project

Usage
-----

Checkout this project and then run:

1. mvn archetype:create-from-project from the project's root directory<br/>

2. replace the following generated file:

    target\generated-sources\archetype\target\classes\META-INF\maven\archetype-metadata.xml

    with the following

    ${projectRoot}\archetype-metadata.xml

3. run mvn install from the folder target/generated-sources/archetype

4. run mvn archetype:generate -DarchetypeCatalog=local in a new folder to create the new project.

5. Maven filters the version attribute in XMl encoding directive <?xml version="1.0" ...> with the version attribute specified for the project so fix these files.

6. To update QueryDSL generated classes as part of the Eclipse build install the lastest version of the QueryDSL m2e plugin by adding a new update site and intsalling the Wclipse plugin as per the instructions below:

    https://github.com/ilx/m2e-querydsl

7. Set the database properties as required and run the unit test.

