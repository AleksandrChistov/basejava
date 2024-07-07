package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int updatedRowsCount = ps.executeUpdate();
                if (updatedRowsCount != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(conn, resume);
            insertContacts(conn, resume);
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
            insertContacts(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery("" +
                        "   SELECT * " +
                        "     FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        putContact(rs, resume);
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
        return sqlHelper.executeTransaction(conn -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "   SELECT * " +
                    "     FROM resume " +
                    " ORDER BY full_name, uuid")
            ) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    resumeMap.put(resume.getUuid(), resume);
                }
            }
            try (PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet result = prepareStatement.executeQuery();
                while (result.next()) {
                    Resume foundResume = resumeMap.get(result.getString("resume_uuid"));
                    if (foundResume != null) {
                        foundResume.putContact(ContactType.valueOf(result.getString("type")), result.getString("value"));
                    }
                }
            }
            return new ArrayList<>(resumeMap.values());
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

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void putContact(ResultSet rs, Resume resume) throws SQLException {
        String contactValue = rs.getString("value");
        if (contactValue != null) {
            resume.putContact(ContactType.valueOf(rs.getString("type")), contactValue);
        }
    }
}
