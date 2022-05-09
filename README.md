# InterIO
Goal: getting comfortable with low level Socket &amp; IO API on the JVM. interIO is a multithreaded http server written in Scala, it follows so called "thread per request model".

InterIO can be used as a lightweight server-side framework for serving http requests.

Wanna play with it? Great! Here are some usage examples:

**clone the repository.**

**start http server via terminal:** 
  > cd into `/InterIO` and start sbt by executing `sbt` in the shell
  
  > execute the following for starting http server: `run localhost 8080`
  
  > open browser and hit `localhost:8080`

**start http server via IDE:**

  > run `InterIO.scala` in order to start http server (if the cmd args are not provided the defaults are: host=localhost, port=8080)

**send requests from the browser to the `localhost:8080`, `localhost:8080/scala`, `localhost:8080/home` endpoints.**

**wanna add your own endpoint? that's more than easy:**
  
  > create the new class for handling the new endpoint. e.g create `LoginHandler` which would listen to `localhost:8080/login`
  
  > `LoginHandler` must extend HttpRequestHandler(httpRequest, httpResponse) in order to be functional.
  
  > `LoginHandler` must override method `handle()` in order to handle http request.
  
  > `LoginHandler` should be registered in the endpoints. Register `LoginHandler` in the method named as `registerPaths()` in HttpServer class

  > the path registering mechanism looks like the following ~> `++("/login", classOf[LoginHandler])`
    
    
**you may see the implementation mechanism of method `handle()` in the following .scala files**:
  > `RootHandler.scala`, `ScalaHandler.scala`, `AboutHandler.scala`

