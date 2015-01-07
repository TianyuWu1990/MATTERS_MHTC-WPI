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
  "IsApproved" boolean NOT NULL DEFAULT false,
  "GroupId" integer NOT NULL,
  CONSTRAINT "PK_users" PRIMARY KEY ("Id"),
  CONSTRAINT "FK_group" FOREIGN KEY ("GroupId")
      REFERENCES mhtc_sch.usergroups ("Id") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "C_unique" UNIQUE ("Email")
);

-- Insert some users
INSERT INTO mhtc_sch.users VALUES (1, 'wpi', 'mhtc-students@wpi.edu', 'WPI', 'Students', 'a5da186fb55ee638cd74ce437ddfa534', true, 1);
INSERT INTO mhtc_sch.users VALUES (nextval('mhtc_sch.user_id_seq'), 'wpi-user', 'mhtc-users@wpi.edu', 'WPI', 'Users', 'a5da186fb55ee638cd74ce437ddfa534', true, 2);

-----------------------------------------------------------
-- This holds user roles such as ROLE_ADMIN, ROLE_USER
-----------------------------------------------------------
CREATE TABLE mhtc_sch.user_roles
(
  "UserName" character varying,
  "Authorities" character varying
);

-- Insert some roles
INSERT INTO mhtc_sch.user_roles VALUES ('wpi', 'ADMIN');
INSERT INTO mhtc_sch.user_roles VALUES ('wpi', 'USER');
INSERT INTO mhtc_sch.user_roles VALUES ('wpi-user', 'USER');


COMMIT;