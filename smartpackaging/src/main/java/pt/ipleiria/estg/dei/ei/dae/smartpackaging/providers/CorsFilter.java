package pt.ipleiria.estg.dei.ei.dae.smartpackaging.providers;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Allows this server to be called by any other server.
        // For development, it can be set to ‘*’.
        // In a production environment, it’s a security risk.
        // You may only specify the servers you allow to communicate.
        // Eg: to communicate only with NUXT ("Access-Control-Allow-Origin", "localhost:3000")
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // important to pass in the preflight browser request
        // note: you enabled this option in the nuxt.config.js (NUXT Web App)
        // axios: {
        //   proxy: true, # tells axios to use a proxy
        //   credentials: true # CORS
        // },
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // defines what headers you authorize that can be present in a request
        // responseContext.getHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
        // defines the verbs you authorize
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers","*");
    }
}
