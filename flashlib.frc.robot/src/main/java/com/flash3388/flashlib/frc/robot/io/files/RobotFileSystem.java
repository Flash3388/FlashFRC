package com.flash3388.flashlib.frc.robot.io.files;

import edu.wpi.first.wpilibj.Filesystem;

import java.nio.file.Path;

/**
 * A utility for accessing the file system on the robot.
 * For wider support and adaptability, it is recommended to this class whenever working with the file system.
 *
 * @since FlashFrc 2020.1.0
 */
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
