-- insert into genre (ID) values ('FANTAZY'), ('ROMAN'), ('HISTORY');
insert into AUTHOR (ID, NAME) values (1, 'EINSHTEIN'), (2, 'LEIBNIZ'), (3, 'KRYZANOVSKY');
-- insert into BOOK (ID, DESCRIPTION, GENRE) values (1, 'LALA', 'FANTAZY'), (2, 'NANA', 'FANTAZY'), (3, 'MN', 'HISTORY');
-- insert into AUTHOR_TO_BOOK (BOOK_ID, AUTHOR_ID) VALUES (1, 2), (1, 1), (2, 3);

INSERT INTO acl_sid (id, principal, sid) VALUES
                                                (1, 0, 'ROLE_ADMIN'),
                                                (2, 0, 'ROLE_USER');

INSERT INTO acl_class (id, class) VALUES
                                         (1, 'com.otus.jdbc.model.Author');

INSERT INTO acl_object_identity
    (id, object_id_class, object_id_identity,
     parent_object, owner_sid, entries_inheriting)
VALUES
       (1, 1, 1, NULL, 1, 0),
       (2, 1, 2, NULL, 1, 0),
       (3, 1, 3, NULL, 1, 0);

INSERT INTO acl_entry
    (id, acl_object_identity, ace_order,
     sid, mask, granting, audit_success, audit_failure)
VALUES
       (1, 1, 1, 1, 1, 1, 1, 1),
       (2, 2, 1, 1, 1, 1, 1, 1),
       (3, 2, 2, 2, 1, 1, 1, 1),
       (4, 3, 1, 2, 1, 1, 1, 1);
