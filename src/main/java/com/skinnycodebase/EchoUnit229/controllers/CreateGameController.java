package com.skinnycodebase.EchoUnit229.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RequestMapping("/api/v1")
@RestController
public class CreateGameController {


    /*
    * Current in production method of generating the join game links
    * for some reason thymeleaf is throwing 'OutOfContext' Exception in v2 of this implementation
    *
    * See ... GameJoinerController.java and joinGame.html under templates for current non working method
    * */
    @GetMapping(value = "/genGame", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String generateGameFromLink(@RequestParam("lobbyID") String input) {

        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE HTML>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">\n" +
                "<head>\n" +
                "    <title>EchoVR Protocol</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                " html, body {\n" +
                "    height: 100%;\n" +
                "}\n" +
                "body {\n" +
                "    background: linear-gradient(#1E7FD9, #01529C);\n" +
                "    color: white;\n" +
                "    font-family: Segoe UI, Helvetica, sans-serif;\n" +
                "    margin: 0;\n" +
                "}\n" +
                ".header {\n" +
                "    padding-top: 60%;\n" +
                "}\n" +
                ".header h1 {\n" +
                "    margin-bottom: 0;\n" +
                "    font-size: 2.2em;\n" +
                "    font-weight: bold;\n" +
                "}\n" +
                ".header h2 {\n" +
                "    margin-top: 0;\n" +
                "    font-weight: lighter;\n" +
                "    font-size: 1.2em\n" +
                "}\n" +
                "hr {\n" +
                "    width:15%;margin:0 auto;\n" +
                "    background-color: white;\n" +
                "    border-color: transparent;\n" +
                "    height: 2px;\n" +
                "}\n" +
                ".flex-item input {\n" +
                "    border-color: transparent;\n" +
                "    border-radius: 25px;\n" +
                "    width: 230px;\n" +
                "    height: 48px;\n" +
                "    color: #2B2D2F;\n" +
                "    background-color: white;\n" +
                "    font-weight: bolder;\n" +
                "    box-shadow: 0px 6px 40px -7px;\n" +
                "}\n" +
                ".flex-item p {\n" +
                "}\n" +
                ".flex-container {\n" +
                "    height: 100%;\n" +
                "    padding: 0;\n" +
                "    margin: 0;\n" +
                "    display: -webkit-box;\n" +
                "    display: -moz-box;\n" +
                "    display: -ms-flexbox;\n" +
                "    display: -webkit-flex;\n" +
                "    display: flex;\n" +
                "    justify-content: center;\n" +
                "}\n" +
                ".row {\n" +
                "    width: auto;\n" +
                "}\n" +
                "#buttons {\n" +
                "    margin-top: 80%;\n" +
                "   margin-top: 88%;\n" +
                "    border-color: transparent;\n" +
                "    border-radius: 25px;\n" +
                "    width: 230px;\n" +
                "    height: 48px;\n" +
                "    color: white;\n" +
                "    background-color: #00519C;\n" +
                "    font-weight: bolder;\n" +
                "    font-size: 1em;\n" +
                "\n" +
                "    box-shadow: 0px 6px 40px -7px grey;" +
                "}\n" +
                ".flex-item {\n" +
                "    text-align: center;\n" +
                "}" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"flex-container\">\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"flex-item\">\n" +
                "                <div class=\"header\">\n" +
                "                <h1>Welcome!</h1>\n" +
                "                <h2>The echoprotocol link has been generated!</h2>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"flex-item\" id=\"buttons\">\n" +
                "                <form>\n" +
                "                    <a id=\"input\" href=\"echoprotocol:");
        builder.append(input);
        builder.append("\" >Click Me</a></form></div></div></div></body></html>");

        return builder.toString();
    }

}
