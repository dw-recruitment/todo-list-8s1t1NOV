# todo-list

"todo list", a coding interview by Anonymous Candidate for Democracy Works.

This is old-school, server-rendered HTML, with simple JDBC hitting a postgres database set-up outside the scope of this code. No react, no REST or micro-services, no Angular, no Docker for the db...

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server
## TODO

- Improve file structure to separate render functions from data manipulations.

- Go full-on REST with a JS framework, possibly Om.

- Dockerize the db setup

## License

Copyright © 2016 Anonymous Candidate
