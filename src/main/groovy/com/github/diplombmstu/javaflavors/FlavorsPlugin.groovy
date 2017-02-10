package com.github.diplombmstu.javaflavors

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * TODO add comment
 */
class FlavorsPlugin implements Plugin<Project> {
    public static final String EXTENSION_NAME = 'flavors'
    private Project project

    @Override
    void apply(Project p) {
        project = p

        setupExtension(project)
        createDeploymentTasks(project)
    }

    private void createDeploymentTasks(final Project project) {
        def config = project.extensions.getByName(EXTENSION_NAME)

        config.all {
            def configInfo = delegate

            themes.all {
                def themeInfo = delegate
                createPrepareTask(themeInfo, configInfo)
            }
        }
    }

    static private setupExtension(Project project) {
        final NamedDomainObjectContainer<Configuration> configContainer =
                project.container(Configuration)

        configContainer.all {
            themes = project.container(Theme)
        }

        project.extensions.add(EXTENSION_NAME, configContainer)
    }

    private createPrepareTask(themeInfo, configInfo) {
        def taskName = "prepare${themeInfo.name}-${configInfo.name}"
        project.task(taskName, type: PrepareTask) {
            description = "Prepare for '${themeInfo.name}'"
            group = "flavors ${configInfo.name}"

            config = configInfo
            theme = themeInfo
        }
    }
}
