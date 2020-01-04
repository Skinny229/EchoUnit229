package com.skinnycodebase.EchoUnit229.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RequestMapping("/api/v1")
@RestController
public class CreateGameController {

    @GetMapping(value ="/genGame",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String generateGameFromLink(@RequestParam("launchparams") String input){

        StringBuilder builder = new StringBuilder();

        //slightly lazy to learn js for now
        builder.append("<!DOCTYPE HTML>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">\n" +
                "<head>\n" +
                "    <title>EchoVR Protocol Maker</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link th:href=\"@{/css/home.css}\" rel=\"stylesheet\" />\n" +
                "\n" +
                "</head>\n" +
                "<body>");
        builder.append("<div class=\"flex-container\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"flex-item\">\n" +
                "            <div class=\"header\">\n" +
                "                <h1>Game Created!</h1>\n" +
                "                <hr>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"flex-item\" id=\"buttons\">\n" +
                "            <form>\n" +
                "                <input href=\"echoprotocol:");
        builder.append(input);
        builder.append("\" value=\"Join Game\"/>\n" +
                "            </form>\n" +
                "\n" +
                "        </div>   </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        return builder.toString();
    }

}
