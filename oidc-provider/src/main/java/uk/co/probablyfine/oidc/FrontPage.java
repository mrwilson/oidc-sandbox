package uk.co.probablyfine.oidc;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.Map;

public class FrontPage {

    public static final MustacheTemplateEngine TEMPLATE = new MustacheTemplateEngine();

    public static String index(Request request, Response response) {
        return TEMPLATE.render(
            new ModelAndView(Map.of(), "index.html")
        );
    }
}
