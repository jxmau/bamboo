```
     ____                  __             
   / __ )____ _____ ___  / /_  ____  ____
  / __  / __ `/ __ `__ \/ __ \/ __ \/ __ \
 / /_/ / /_/ / / / / / / /_/ / /_/ / /_/ /
/_____/\__,_/_/ /_/ /_/_.___/\____/\____/
```
![Java](https://img.shields.io/badge/java_16-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

Bamboo is a simple terminal based To-Do app.

Library used :
* JSON-Simple


<h2> Command </h2>

<h3> Main Shell </h3>

| Command Line | Description |
| --- | --- |
| new < listName > | Will create a new list. |
| cd < listName >| Will enter in the shell of the list. |
| ls | Will list all of the list saved. |
| delete < listName > | Will delete a list. |
| help | Will show the list of commands of the main Shell. |
| exit | Will end the program. |

<h3> Task Shell </h3>

Note : the tasks will have their index printed before them for you to select them.

| Command Line | Description |
| --- | --- |
| new < description > | Will create a new task. |
| delete < index > | Will delete the task. |
| done/undone < index > | Will permute the status of the task. |
| modify < index > < description > |  Will modify the description of the task. |
| hide/show | Will hide or show the already done tasks. |
| clear | Will delete every tasks. |
| help | Will show all the commands.  |
| exit | Will return to the main shell.  |

<h2> Road map </h2>

* Code Maintainability 
  * Write test routine for the shells' methods.
* Features 
  * Sort tasks between important and not.
  * Develop sub-tasks.
* Others
  * Link to a RESTful Server to sync tasks.


<h2> License </h2>

Bamboo is under the CC 4.0 BY license.