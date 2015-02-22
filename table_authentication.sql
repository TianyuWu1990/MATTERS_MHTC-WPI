BEGIN;

-- Clear the tables and sequences
DROP TABLE IF EXISTS mhtc_sch.admins;
DROP TABLE IF EXISTS mhtc_sch.users CASCADE;
DROP TABLE IF EXISTS mhtc_sch.user_roles;
DROP TABLE IF EXISTS mhtc_sch.usergroups CASCADE;
DROP SEQUENCE IF EXISTS mhtc_sch.user_id_seq;


-- Create them again, fresh.
CREATE SEQUENCE mhtc_sch.user_id_seq START 2;

-----------------------------------------------------------
-- User group table
-----------------------------------------------------------
CREATE TABLE mhtc_sch.usergroups
(
  "Id" integer NOT NULL,
  "Name" character varying(256) NOT NULL,
  CONSTRAINT "PK_groups" PRIMARY KEY ("Id")
);

INSERT INTO mhtc_sch.usergroups VALUES (1, 'Administrators'), (2, 'Users');

-----------------------------------------------------------
-- User data table.
-----------------------------------------------------------
CREATE TABLE mhtc_sch.users
(
  "Id" integer NOT NULL DEFAULT nextval('mhtc_sch.user_id_seq'::regclass),
  "UserName" character varying,
  "Email" character varying(256) NOT NULL,
  "FirstName" character varying(25),
  "LastName" character varying(25),
  "PasswordHash" text NOT NULL,
  "Token" text NOT NULL,
  CONSTRAINT "PK_users" PRIMARY KEY ("Id"),
  CONSTRAINT "C_unique" UNIQUE ("Email"),
  CONSTRAINT "C_username" UNIQUE ("UserName")
);

-- Insert some users
INSERT INTO mhtc_sch.users VALUES (1, 'wpi', 'mhtc-students@wpi.edu', 'WPI', 'Students', 'a5da186fb55ee638cd74ce437ddfa534', '');
INSERT INTO mhtc_sch.users VALUES (nextval('mhtc_sch.user_id_seq'), 'wpi-user', 'mhtc-users@wpi.edu', 'WPI', 'Users', 'a5da186fb55ee638cd74ce437ddfa534', '');

-----------------------------------------------------------
-- This holds user roles such as ROLE_ADMIN, ROLE_USER
-----------------------------------------------------------
CREATE TABLE mhtc_sch.user_roles
(
  "UserName" character varying,
  "Authorities" character varying,
  CONSTRAINT "C_username" FOREIGN KEY ("UserName")
    REFERENCES mhtc_sch.users ("UserName") MATCH SIMPLE
    ON DELETE CASCADE
);

-- Insert some roles
INSERT INTO mhtc_sch.user_roles VALUES ('wpi', 'ADMIN'), ('wpi', 'USER');
INSERT INTO mhtc_sch.user_roles VALUES ('wpi-user', 'USER');

-- Function: mhtc_sch.insertuser(text, text, text, text, text)

-- DROP FUNCTION mhtc_sch.insertuser(text, text, text, text, text);

CREATE OR REPLACE FUNCTION mhtc_sch.insertuser(username text, email text, firstname text, lastname text, password text)
  RETURNS void AS
$BODY$
BEGIN
    INSERT INTO mhtc_sch.users("UserName", "Email", "FirstName", "LastName", "PasswordHash", "Token")
    VALUES (username, email, firstname, lastname, md5(password), '');

    INSERT INTO mhtc_sch.user_roles("UserName", "Authorities")
    VALUES (username, "USER");
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.insertuser(text, text, text, text, text, text)
  OWNER TO postgres;

-- Function: mhtc_sch.insertadmin(text, text, text, text, text)

-- DROP FUNCTION mhtc_sch.insertadmin(text, text, text, text, text);

CREATE OR REPLACE FUNCTION mhtc_sch.insertadmin(username text, email text, firstname text, lastname text, password text)
    RETURNS void AS
$BODY$
BEGIN
    INSERT INTO mhtc_sch.users("UserName", "Email", "FirstName", "LastName", "PasswordHash", "Token")
    VALUES (username, email, firstname, lastname, md5(password), '');

    INSERT INTO mhtc_sch.user_roles("UserName", "Authorities")
    VALUES (username, "ADMIN");

    INSERT INTO mhtc_sch.user_roles("UserName", "Authorities")
    VALUES (username, "USER");
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.insertuser(text, text, text, text, text, text)
  OWNER TO postgres;
    
-- Function: mhtc_sch.getuser(text)

-- DROP FUNCTION mhtc_sch.getuser(text);

CREATE OR REPLACE FUNCTION mhtc_sch.getuser(username text)
  RETURNS SETOF mhtc_sch.users AS
$BODY$
BEGIN
    RETURN QUERY SELECT "Id", "UserName", "Email", "FirstName", "LastName", "PasswordHash" FROM mhtc_sch.users WHERE "UserName" = username;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.getuser(text)
  OWNER TO postgres;

-- Function: mhtc_sch.updatepassword(text, text)

-- DROP FUNCTION mhtc_sch.updatepassword(text, text);

CREATE OR REPLACE FUNCTION mhtc_sch.updatepassword(username text, password text)
  RETURNS void AS
$BODY$
BEGIN
    UPDATE mhtc_sch.users 
        SET "PasswordHash" = md5(password)
        WHERE "UserName" = username;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.updatepassword(text, text)
  OWNER TO postgres;

COMMIT;