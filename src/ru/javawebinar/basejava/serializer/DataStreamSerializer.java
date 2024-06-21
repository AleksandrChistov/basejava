package ru.javawebinar.basejava.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = r.getSections();
            writeWithException(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                Section section = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        String content = ((TextSection) section).getContent();
                        dos.writeUTF(content);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = ((ListSection) section).getItems();
                        writeWithException(dos, items, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgs = ((OrganizationSection) section).getOrganizations();
                        writeWithException(dos, orgs, org -> {
                            dos.writeUTF(org.getName());
                            Optional<String> website = Optional.ofNullable(org.getWebsite());
                            dos.writeUTF(website.orElse("null"));
                            List<Organization.Period> periods = org.getPeriods();
                            writeWithException(dos, periods, period -> {
                                dos.writeUTF(period.getTitle());
                                Optional<String> description = Optional.ofNullable(period.getDescription());
                                dos.writeUTF(description.orElse("null"));
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> {
                ContactType type = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.putContact(type, value);
            });
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        String content = dis.readUTF();
                        resume.putSection(sectionType, new TextSection(content));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = new ArrayList<>();
                        readWithException(dis, () -> {
                            String item = dis.readUTF();
                            items.add(item);
                        });
                        resume.putSection(sectionType, new ListSection(items));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgs = new ArrayList<>();
                        readWithException(dis, () -> {
                            String name = dis.readUTF();
                            String website = dis.readUTF();
                            website = website.equals("null") ? null : website;
                            List<Organization.Period> periods = new ArrayList<>();
                            readWithException(dis, () -> {
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                description = description.equals("null") ? null : description;
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                periods.add(new Organization.Period(startDate, endDate, title, description));
                            });
                            orgs.add(new Organization(name, website, periods));
                        });
                        resume.putSection(sectionType, new OrganizationSection(orgs));
                    }
                }
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface Writable<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface Readable {
        void read() throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writable<T> writable) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writable.write(t);
        }
    }

    private void readWithException(DataInputStream dis, Readable readable) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readable.read();
        }
    }
}
