Angular.dart/Scala/Spray/Akka

This is the recipe application from the basic Angular.dart tutorial (https://angulardart.org/tutorial/08-ch06-view.html) extended with a backend written in Scala, using Akka and Spray.

Compared to the original recipe application, I am going to add editing and adding recipes as suggested in the tutorial as an exercise. In addition, I am going to implement the webservice basing on the template available here: http://typesafe.com/activator/template/dart-akka-spray

The text below comes from the dart-akka-spray template.

Scala/Spray/Akka/Dart
=====================

This is a very rudimentary application&mdash;I'll eventually turn it into a Giter8 template&mdash;that is intended to demonstrate how to get Scala/Spray/Akka to work nicely with Dart. Fortunately, it's not that hard to get started. You just need to be aware of a few things.

The key (and sticking point for me initially) is to use Spray's `getFromDirectory` directive to serve up the directory in which your Dart application is housed (in this case `src/main/webapp`). Once you've done that, you need to navigate to the following directory in Chromium (or another browser running the Dart VM):

    http://localhost:3000/index.html

The one other functioning REST endpoint is the `/api/tasks` endpoint, which will simply deliver up a JSON array as a string. This string will be consumed by the Dart application and used to render a simple `<ul>` containing three tasks.

### Getting Started

First things first, make sure that you've installed SBT and Chromium. If so, all you need to do is run `sbt run` and then navigate to the URL mentioned above in Chromium.

The app will also run in other browsers because it includes compiled Javascript. If you make changes to any `.dart` files, make sure to re-compile the JavaScript.

### Things to Notice

* This application used [Thomas Lockney](https://github.com/tlockney)'s [Spray/Akka example](https://github.com/tlockney/akka-spray-example) as an initial template.
* The application is Akka-driven at its very core, as you can see in the `Application.scala` and `Starter.scala` files.
* The main server&mdash;housed in `MainServer.scala`&mdash;inherits from a more general `WebService` trait. I think that defining such a `trait` is a good way to ensure that the web services that you tie together&mdash;something that Spray makes very easy&mdash;function predictably.
* The various web services (here there is only one, but others can be added) are supervised by a `ServerSupervisor` actor. Within this `Actor`'s definition, a variety of route schemes can be concatenated and run with one `runRoute` function.
* If you're working on the Dart application separately in the Dart Editor, you will constantly see errors like the following:
    ```
    XMLHttpRequest cannot load http://localhost:3000/api/tasks.
    No 'Access-Control-Allow-Origin' header is present on the requested resource.
    Origin 'http://127.0.0.1:3030' is therefore not allowed access.
    Uncaught Error: Instance of '_XMLHttpRequestProgressEvent@0x3918afae'
    Exception: Instance of '_XMLHttpRequestProgressEvent@0x3918afae'
    undefined (undefined:0:0)
    ```
This happens because the Dart Editor expects you to be using CORS and doesn't realize that the application is being served up by something else. If you know of a good way to overcome this issue, please let me know!
