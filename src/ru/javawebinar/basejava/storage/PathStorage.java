package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamStorage streamStorage;

    protected PathStorage(String dir, StreamStorage streamStorage) {
        final Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable/writable");
        }
        this.directory = directory;
        this.streamStorage = streamStorage;
    }

    @Override
    public void clear() {
        try (Stream<Path> stream = Files.walk(directory)) {
            stream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Clear directory error", null);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> stream = Files.list(directory)) {
            return (int) stream.count();
        } catch (IOException e) {
            throw new StorageException("Getting size error", null);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            streamStorage.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Update path error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
            streamStorage.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Save path error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamStorage.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Get path error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            if (!Files.isDirectory(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new StorageException("Delete path error", path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.map(this::doGet).toList();
        } catch (IOException e) {
            throw new StorageException("Copy all paths error", null);
        }
    }
}
