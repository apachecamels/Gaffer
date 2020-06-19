package uk.gov.gchq.gaffer.integration.impl.loader.resolvers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import uk.gov.gchq.gaffer.integration.impl.loader.schemas.SchemaLoader;

public class SchemaLoaderParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == SchemaLoader.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return null; //parameterContext.getParameter().getParameterizedType();
    }
}
