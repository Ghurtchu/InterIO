# InterIO
Goal: getting comfortable with low level Socket &amp; IO API on the JVM. interIO is a multithreaded http server written in Scala, it follows so called "thread per request model".

InterIO can be used as a lightweight server-side framework for serving http requests.

Wanna play with it? Great! Here are some usage examples:

> clone the repository.

> start http server via terminal: 
  1) cd into `/InterIO` and start sbt by executing `sbt` in the shell:
  2) then execute the following: `run localhost 8080`
  3) open browser and hit `localhost:8080`.

> start http server via IDE:

> 1) run `InterIO.scala` in order to start http server (if the cmd args are not provided the defaults are: host=localhost, port=8080).
> send requests from the browser to the `localhost:8080`, `localhost:8080/scala`, `localhost:8080/home` endpoints.

> wanna add your own endpoint? that's more than easy:
  
  1) create the new class for handling the new endpoint. e.g create `LoginHandler` which would listen to `localhost:8080/login`
  2) `LoginHandler` must extend HttpRequestHandler(httpRequest, httpResponse) in order to be functional.
  3) `LoginHandler` must override method `handle()` in order to handle http request.
