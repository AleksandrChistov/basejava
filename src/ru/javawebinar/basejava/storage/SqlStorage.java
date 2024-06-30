package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.utils.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.executeQuery(connectionFactory, "DELETE FROM resume", ps -> {
            ps.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        SqlHelper.executeQuery(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int updatedRowsCount = ps.executeUpdate();
            if (updatedRowsCount != 1) {
                throw new NotExistStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public void save(Resume resume) {
        SqlHelper.executeQuery(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.executeQuery(connectionFactory, "SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.executeQuery(connectionFactory, "DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            int updatedRowsCount = ps.executeUpdate();
            if (updatedRowsCount != 1) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.executeQuery(connectionFactory, "SELECT * FROM resume", ps -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                list.add(resume);
            }
            return list.stream().sorted().toList();
        });
    }

    @Override
    public int size() {
        return SqlHelper.executeQuery(connectionFactory, "SELECT COUNT(*) as rows_count FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("There are no resumes");
            }
            return rs.getInt("rows_count");
        });
    }
}
