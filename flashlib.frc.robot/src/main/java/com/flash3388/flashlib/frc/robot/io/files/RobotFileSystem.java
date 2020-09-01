package com.flash3388.flashlib.frc.robot.io.files;

import edu.wpi.first.wpilibj.Filesystem;

import java.nio.file.Path;

public class RobotFileSystem {

    private final Path mBasePath;

    public RobotFileSystem(Path basePath) {
        mBasePath = basePath;
    }

    public RobotFileSystem() {
        this(Filesystem.getOperatingDirectory().toPath());
    }

    public Path getPath(String relativePath) {
        return mBasePath.resolve(relativePath);
    }

    public Path getPath(Path relativePath) {
        return mBasePath.resolve(relativePath);
    }

    public Path getDeployDirectory() {
        return Filesystem.getDeployDirectory().toPath();
    }

    public Path getOperatingDirectory() {
        return mBasePath;
    }
}
