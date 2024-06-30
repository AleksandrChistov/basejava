package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS_FILE = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream(PROPS_FILE)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS_FILE.getAbsolutePath(), e);
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
