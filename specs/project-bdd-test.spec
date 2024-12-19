Projet creation specification
=====================

Project creation BDD test
----------------

Мы создадим новый проект, назначим ему менеджера.
Затем проверим, что проект создан корректно. Будем использовать библиотеку
 Selenide.

* Open application in the browser

* Log in as user with "admin" username and "admin" password

* Open the project list view

* Open the project detail view to create new instance

* Fill form fields with following values: name is "BDD testing", manager name is "admin"

* Save new project

* Make sure the new project with "BDD testing" name is added to project table

* Remove test project

