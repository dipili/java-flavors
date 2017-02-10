package com.github.diplombmstu.javaflavors

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.util.GFileUtils

/**
 * TODO add comment
 */
class PrepareTask extends DefaultTask {
    Theme theme

    Configuration config

    @TaskAction
    prepare() {
        println "Preparing sources for theme ${theme.id}..."

        if (new File(config.resourceFolder).exists())
            GFileUtils.cleanDirectory(new File(config.resourceFolder))

        println 'Moving resources...'

        processFolder(new File(config.defaultResourceFolder), "${theme.resourceDir}")
    }

    private processFolder(File folder, String selectedResourcePath) {
        for (File entry : folder.listFiles()) {
            if (entry.isDirectory())
                processFolder(entry, selectedResourcePath)
            else
                processFile(entry, selectedResourcePath)
        }
    }

    private processFile(File entry, String selectedResourcePath) {
        String relativeFilePath = entry.getPath().substring(config.defaultResourceFolder.length())

        File mainFile = new File(String.format("%s/%s", config.resourceFolder, relativeFilePath))
        File selectedFile = new File(String.format("%s/%s", selectedResourcePath, relativeFilePath))

        if (selectedFile.exists())
            GFileUtils.copyFile(selectedFile, mainFile)
        else
            GFileUtils.copyFile(entry, mainFile)
    }
}

