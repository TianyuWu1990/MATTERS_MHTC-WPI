--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.1
-- Started on 2014-12-05 13:42:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = mhtc_sch, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 195 (class 1259 OID 45456)
-- Name: logs; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE mhtc_sch."logs" (
    id integer DEFAULT nextval('log_id'::regclass) NOT NULL,
    log_datetime character varying(50) DEFAULT now() NOT NULL,
    component_name text NOT NULL,
    message text NOT NULL,
    status character varying NOT NULL
);


ALTER TABLE mhtc_sch."logs" OWNER TO postgres;

--
-- TOC entry 2042 (class 0 OID 45456)
-- Dependencies: 195
-- Data for Name: logs; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY logs (id, log_datetime, component_name, message, status) FROM stdin;


--
-- TOC entry 1934 (class 2606 OID 45463)
-- Name: logs_pkey; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mhtc_sch."logs"
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);


-- Completed on 2014-12-05 13:42:22

--
-- PostgreSQL database dump complete
--

