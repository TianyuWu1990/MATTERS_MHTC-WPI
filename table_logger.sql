DROP SEQUENCE IF EXISTS mhtc_sch.log_id CASCADE;

DROP TABLE IF EXISTS mhtc_sch.logs;
CREATE SEQUENCE mhtc_sch.log_id;

CREATE TABLE mhtc_sch.logs
(
	  id integer NOT NULL DEFAULT nextval('mhtc_sch.log_id'::regclass),
	  moment character varying(50) NOT NULL DEFAULT now(),
	  job text NOT NULL,
	  message text NOT NULL,
	  code integer,
	  priority integer,
	  origin character varying(100) DEFAULT NULL::character varying,
	  CONSTRAINT logs_pkey PRIMARY KEY (id)
);