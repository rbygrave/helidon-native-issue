package org.example;

import io.helidon.common.media.type.MediaTypes;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

import static io.helidon.http.Status.*;

public class Main {

    public static void main(String[] args) {

      WebServer.builder()
        .routing( routing -> {
          routing.get("/hi", Main::_getHi);
          routing.post("/basic", Main::_postHandler);
        })
        .port(8080)
        .build()
        .start();
    }

  private static void _getHi(ServerRequest req, ServerResponse res) {
    res.status(OK_200);
    res.headers().contentType(MediaTypes.TEXT_PLAIN);
    res.send("Hello world!");
  }

  private static void _postHandler(ServerRequest req, ServerResponse res) {
    var body = req.content().as(String.class);
    var result = "Got[" + body + "]";
    res.status(CREATED_201);
    res.headers().contentType(MediaTypes.TEXT_PLAIN);
    res.send(result);
  }
}
