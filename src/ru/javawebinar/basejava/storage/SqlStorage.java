package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeQuery("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int updatedRowsCount = ps.executeUpdate();
            if (updatedRowsCount != 1) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery("" +
                        "SELECT * " +
                        "  FROM resume r " +
                        "  LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        " WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume.putContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            int updatedRowsCount = ps.executeUpdate();
            if (updatedRowsCount != 1) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeQuery("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = get(rs.getString("uuid"));
                list.add(resume);
            }
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT COUNT(*) as rows_count FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("There are no resumes");
            }
            return rs.getInt("rows_count");
        });
    }
}
