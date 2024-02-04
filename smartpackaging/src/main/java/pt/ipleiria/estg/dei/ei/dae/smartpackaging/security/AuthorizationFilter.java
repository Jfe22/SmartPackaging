/*
In the following cases, class level annotation:

@PermitAll is specified specified on methods ofthe method level annotations take precedence over the
at the class level and @RolesAllowed or @DenyAll are the same class;

@DenyAll is specified at the class level and @PermitAll or @RolesAllowed are specified on methods of the same class;

@RolesAllowed is specified at the class level and @PermitAll or @DenyAll are specified on methods of the same class.
*/

package pt.ipleiria.estg.dei.ei.dae.smartpackaging.security;

import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

@Provider
@Authenticated
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private static final Response ACCESS_DENIED = Response.status(401).entity("Access denied for this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(403).entity("Access forbidden for this resource").build();

    @Context
    private SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        var methodInvoker = (ResourceMethodInvoker) containerRequestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");

        Method method = methodInvoker.getMethod();

        var resource = method.getDeclaringClass();

        //if auth, acces granted for all roles
        if (resource.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);

                var roles = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                //is user valid?
                if (roles.stream().anyMatch(securityContext::isUserInRole)) {
                    return;
                }

                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
        }

        // access denied for all
        if (resource.isAnnotationPresent(DenyAll.class)) {
            if (method.isAnnotationPresent(PermitAll.class)) {
                return;
            }

            //verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed beanRolesAnnotation = method.getAnnotation(RolesAllowed.class);
                var roles = new HashSet<>(Arrays.asList(beanRolesAnnotation.value()));

                //is user valid?
                if (roles.stream().anyMatch(securityContext::isUserInRole)) {
                    return;
                }
            }

            containerRequestContext.abortWith(ACCESS_DENIED);
            return;
        }

        if (resource.isAnnotationPresent(RolesAllowed.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            if (method.isAnnotationPresent(PermitAll.class)) {
                return;
            }

            RolesAllowed rolesAnnotation = resource.getAnnotation(RolesAllowed.class);
            var roles = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            //verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                roles.addAll(Arrays.asList(rolesAnnotation.value()));
            }

            //is user valid?
            if (roles.stream().anyMatch(securityContext::isUserInRole)) {
                return;
            }

            containerRequestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            containerRequestContext.abortWith(ACCESS_DENIED);
            return;
        }

        if (method.isAnnotationPresent(PermitAll.class))
            return;

        //verify user access
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            var roles = new HashSet<>(Arrays.asList(method.getAnnotation(RolesAllowed.class).value()));

            //is user valid
            if (roles.stream().anyMatch(securityContext::isUserInRole))
                return;

            containerRequestContext.abortWith(ACCESS_FORBIDDEN);
        }

    }

}



