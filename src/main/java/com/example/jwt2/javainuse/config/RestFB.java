package com.example.jwt2.javainuse.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class RestFB {
    @Autowired
    private Environment env;
    public String getToken(final String code) throws IOException {
        String link = String.format(Objects.requireNonNull(env.getProperty("facebook.link.get.token")), env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"), env.getProperty("facebook.redirect.uri"), code);
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public com.restfb.types.User getUserInfo(final String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, env.getProperty("facebook.app.secret"),
                Version.LATEST);
        return facebookClient.fetchObject("me", com.restfb.types.User.class);
    }

}