package com.github.diplombmstu.javaflavors

import org.gradle.api.NamedDomainObjectContainer

/**
 * The plugin root configuration
 */
class Configuration {
    NamedDomainObjectContainer<Theme> themes

    String name

    String resourceFolder

    String defaultResourceFolder

    Configuration(final String name) {
        this.name = name
    }

    def themes(final Closure configureClosure) {
        themes.configure(configureClosure)
    }
}
