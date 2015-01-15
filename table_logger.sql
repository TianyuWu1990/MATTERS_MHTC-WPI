DROP SEQUENCE IF EXISTS mhtc_sch.log_id CASCADE;

DROP TABLE IF EXISTS mhtc_sch.logs;
CREATE SEQUENCE mhtc_sch.log_id;

CREATE TABLE mhtc_sch."logs" (
    id integer DEFAULT nextval('mhtc_sch.log_id'::regclass) NOT NULL,
    log_datetime character varying(50) DEFAULT now() NOT NULL,
    component_name text NOT NULL,
    message text NOT NULL,
    status character varying NOT NULL
);


ALTER TABLE ONLY mhtc_sch."logs"
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);
