package uk.co.probablyfine.oidc;

import spark.Spark;

public class ProviderMain {

    public static void main(String... args) {

        Spark.get("/", (req, res) -> "Hello, world!");

    }
}
