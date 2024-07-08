package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlBiExecutor;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String driverPath, String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName(driverPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
            deleteRows(conn, "DELETE FROM contact WHERE resume_uuid = ?", resume);
            deleteRows(conn, "DELETE FROM section WHERE resume_uuid = ?", resume);
            insertContacts(conn, resume);
            insertSections(conn, resume);
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
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery("" +
                        "SELECT " +
                        "       r.full_name, " +
                        "       c.type as type, " +
                        "       c.value as value, " +
                        "       s.type as section_type, " +
                        "       s.value as section_value " +
                        "  FROM resume r " +
                        "  JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "  JOIN section s " +
                        "       ON r.uuid = s.resume_uuid " +
                        " WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        putContact(rs, resume);
                        putSection(rs, resume);
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
            fillInResumeMap(conn, "SELECT * FROM contact", resumeMap, this::putContact);
            fillInResumeMap(
                    conn,
                    "SELECT " +
                            "type as section_type, " +
                            "value as section_value, " +
                            "resume_uuid " +
                            "FROM section",
                    resumeMap,
                    this::putSection
            );
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

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                SectionType sectionType = SectionType.valueOf(e.getKey().name());
                Section section = e.getValue();
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        String content = ((TextSection) section).getContent();
                        ps.setString(3, content);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = ((ListSection) section).getItems();
                        String content = String.join("\n", items);
                        ps.setString(3, content);
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteRows(Connection conn, String sql, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

    private void putSection(ResultSet rs, Resume resume) throws SQLException {
        String sectionValue = rs.getString("section_value");
        if (sectionValue != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> resume.putSection(sectionType, new TextSection(sectionValue));
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    String[] strings = sectionValue.split("\n");
                    resume.putSection(sectionType, new ListSection(strings));
                }
            }
        }
    }

    private void fillInResumeMap(Connection conn, String sql, Map<String, Resume> resumeMap, SqlBiExecutor<ResultSet, Resume> executor) {
        try (PreparedStatement prepareStatement = conn.prepareStatement(sql)) {
            ResultSet result = prepareStatement.executeQuery();
            while (result.next()) {
                Resume foundResume = resumeMap.get(result.getString("resume_uuid"));
                if (foundResume != null) {
                    executor.execute(result, foundResume);
                }
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
