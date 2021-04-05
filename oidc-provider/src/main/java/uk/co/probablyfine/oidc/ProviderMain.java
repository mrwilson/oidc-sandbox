package uk.co.probablyfine.oidc;

import static spark.Spark.get;

public class ProviderMain {

    public static void main(String... args) {

        get("/", FrontPage::index);

        get("/authorize", AuthorizeAction::authorize);

    }
}
