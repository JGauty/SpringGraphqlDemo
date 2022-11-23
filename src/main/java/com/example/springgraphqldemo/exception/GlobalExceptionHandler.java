package com.example.springgraphqldemo.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof PaymentException)
            return convertToGraphQlError(ex);
        else if (ex instanceof ConstraintViolationException)
            return handleConstraintViolationException((ConstraintViolationException) ex);
        else if (ex instanceof BindException)
            return handleSpringConstraintViolationException((BindException)ex);
        else if (ex instanceof Exception)
            return convertToGraphQlError(ex);
        else
            return super.resolveToSingleError(ex, env);
    }

    private GraphQLError convertToGraphQlError(Throwable ex) {
        log.warn("Exception while handling request: {}", ex);
        return GraphqlErrorBuilder.newError().message(ex.getMessage())
                .errorType(ErrorType.DataFetchingException).build();
    }

    private GraphQLError handleConstraintViolationException(ConstraintViolationException ex) {
        Set<String> errorMessages = new HashSet();
        ex.getConstraintViolations().forEach(it ->
                errorMessages.add("Field" +  it.getPropertyPath() + " " + it.getMessage()
                        +", but value was " + it.getInvalidValue()));
        String message = errorMessages.stream().collect(Collectors.joining("\n"));
        log.warn("Exception while handling request: message", ex.getMessage());
        return GraphqlErrorBuilder.newError().message(message).errorType(ErrorType.DataFetchingException).build();
    }

    private GraphQLError handleSpringConstraintViolationException(BindException ex) {
        Set<String> errorMessages = new HashSet();
        ex.getFieldErrors().forEach(it ->
                errorMessages.add("Field " + it.getField()
                        +" does not have valid value " + it.getRejectedValue()));
        String message = errorMessages.stream().collect(Collectors.joining("\n"));
        log.warn("Constraint violation while handling request: message", ex.getMessage());
        return GraphqlErrorBuilder.newError().message(message)
                .errorType(ErrorType.ValidationError).build();
    }
}
