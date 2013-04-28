package com.codahale.dropwizard.configuration;

import com.codahale.dropwizard.validation.ConstraintViolations;
import com.google.common.collect.ImmutableSet;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * An exception thrown where there is an error parsing a configuration object.
 */
public class ConfigurationException extends Exception {
    private static final long serialVersionUID = 5325162099634227047L;

    private final ImmutableSet<ConstraintViolation<?>> constraintViolations;

    /**
     * Creates a new ConfigurationException for the given path with the given errors.
     *
     * @param path      the bad configuration path
     * @param errors    the errors in the path
     */
    public <T> ConfigurationException(String path, Set<ConstraintViolation<T>> errors) {
        super(formatMessage(path, ConstraintViolations.format(errors)));
        this.constraintViolations = ConstraintViolations.copyOf(errors);
    }

    public ImmutableSet<ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }

    private static String formatMessage(String file, Iterable<String> errors) {
        final StringBuilder msg = new StringBuilder(file)
                .append(" has the following errors:\n");
        for (String error : errors) {
            msg.append("  * ").append(error).append('\n');
        }
        return msg.toString();
    }
}
