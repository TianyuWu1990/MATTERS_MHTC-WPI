--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: mhtc_sch; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA mhtc_sch;


ALTER SCHEMA mhtc_sch OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = mhtc_sch, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE categories (
    "Id" integer NOT NULL,
    "Name" character varying(256) NOT NULL,
    "ParentId" integer,
    "Visible" boolean DEFAULT true NOT NULL,
    "Source" character varying(256)
);


ALTER TABLE mhtc_sch.categories OWNER TO postgres;

--
-- Name: getcategories(boolean, integer); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION getcategories(showall boolean DEFAULT false, parentid integer DEFAULT NULL::integer) RETURNS SETOF categories LANGUAGE plpgsql IMMUTABLE begin if parentId is null then	return QUERY select	"Id", "Name", "ParentId", "Visible", "Source" from mhtc_sch.categories where "Visible" = case when showAll = true then "Visible" else true end and "ParentId" is null order by "Id"; else return QUERY select	"Id", "Name", "ParentId", "Visible", "Source" from mhtc_sch.categories where "Visible" = case when showAll = true then "Visible" else true end and "ParentId" = parentId order by "Id"; end if; end;

ALTER FUNCTION mhtc_sch.getcategories(showall boolean, parentid integer) OWNER TO postgres;


--
-- Name: getcategorybyid(integer); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION getcategorybyid(categoryid integer) RETURNS SETOF categories
    LANGUAGE plpgsql IMMUTABLE
    AS $$
  begin	
	return QUERY
	select	"Id",
		"Name",
		"ParentId",
		"Visible",
		"Source"
	from mhtc_sch.categories
	where "Id" = categoryId;
 end;
$$;


ALTER FUNCTION mhtc_sch.getcategorybyid(categoryid integer) OWNER TO postgres;

--
-- Name: getdatabymetric(integer); Type: FUNCTION; Schema: mhtc_sch; Owner: ahoxha
--

CREATE FUNCTION getdatabymetric(metricid integer) RETURNS TABLE("Stateid" integer, "StateName" character varying, "Abbreviation" character, "Year" integer, "Value" double precision)
    LANGUAGE plpgsql IMMUTABLE
    AS $$
  begin	
	return QUERY
	select	s."StateId" StateId
		,st."Name" StateName
		,st."Abbreviation"
		,s."Year"
		,s."Value"
	from mhtc_sch.statistics s
		inner join mhtc_sch.states st on s."StateId" = st."Id"
	where s."MetricId" = metricId;
 end;
$$;


ALTER FUNCTION mhtc_sch.getdatabymetric(metricid integer) OWNER TO ahoxha;

--
-- Name: getdatabystate(integer); Type: FUNCTION; Schema: mhtc_sch; Owner: ahoxha
--

CREATE FUNCTION getdatabystate(stateid integer) RETURNS TABLE("MetricId" integer, "MetricName" character varying, "Year" integer, "Value" double precision)
    LANGUAGE plpgsql IMMUTABLE
    AS $$
  begin	
	return QUERY
	select	m."Id" MetricId
		,m."Name" MetricName
		,s."Year"
		,s."Value"
		--,c."Name" CategoryName
	from mhtc_sch.statistics s
		inner join mhtc_sch.categoriesxmetrics cxm on s."MetricId" = cxm."metricId"
		inner join mhtc_sch.metrics m on cxm."metricId" = m."Id"
		inner join mhtc_sch.categories c on cxm."categoryId" = c."Id"
	where s."StateId" = stateId;
 end;
$$;


ALTER FUNCTION mhtc_sch.getdatabystate(stateid integer) OWNER TO ahoxha;

--
-- Name: getmetrics(integer, boolean); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION getmetrics(categoryid integer, showall boolean DEFAULT false) RETURNS TABLE("Id" integer, "Name" character varying, "Visible" boolean, "IsCalculated" boolean)
    LANGUAGE plpgsql IMMUTABLE
    AS $$
  begin	
	return QUERY
	select	m."Id",
		m."Name",
		m."Visible",
		m."IsCalculated"
	from mhtc_sch.metrics m
		inner join mhtc_sch.categoriesxmetrics cxm on m."Id" = cxm."metricId"
	where cxm."categoryId" = categoryId
		and m."Visible" = case when showAll = true then m."Visible" else true end
	order by "Id";
 end;
$$;


ALTER FUNCTION mhtc_sch.getmetrics(categoryid integer, showall boolean) OWNER TO postgres;

--
-- Name: states; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE states (
    "Id" integer NOT NULL,
    "Abbreviation" character(2) NOT NULL,
    "Name" character varying(40) NOT NULL,
    "IsPeerState" boolean DEFAULT false NOT NULL
);


ALTER TABLE mhtc_sch.states OWNER TO postgres;

--
-- Name: getstatebyid(integer); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION getstatebyid(id integer) RETURNS SETOF states
    LANGUAGE plpgsql IMMUTABLE
    AS $$
  begin
	SELECT * FROM mhtc_sch.states WHERE "Id" = id;
  end;
  $$;


ALTER FUNCTION mhtc_sch.getstatebyid(id integer) OWNER TO postgres;

--
-- Name: getstates(boolean); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION getstates(showonlypeerstates boolean DEFAULT false) RETURNS SETOF states
    LANGUAGE plpgsql IMMUTABLE
    AS $$
  begin
	return QUERY
	select 	"Id", 
		"Abbreviation",
		"Name",
		"IsPeerState" 
	from mhtc_sch.states 
	where "IsPeerState" = 
			case when showOnlyPeerStates = true 
			     then showOnlyPeerStates else "IsPeerState" 
			end
	order by "Id";
 end;
$$;


ALTER FUNCTION mhtc_sch.getstates(showonlypeerstates boolean) OWNER TO postgres;

--
-- Name: getvalue(integer, integer, integer); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION getvalue(mid integer, y integer, stid integer) RETURNS double precision
    LANGUAGE plpgsql IMMUTABLE
    AS $$
declare
	val double precision;
  begin
	select "Value" from mhtc_sch.statistics s1 where s1."MetricId" = mId and s1."StateId" = stId and s1."Year" = y into val;
	return val;
  end;
  $$;


ALTER FUNCTION mhtc_sch.getvalue(mid integer, y integer, stid integer) OWNER TO postgres;

--
-- Name: insertcategory(character varying, integer, character varying); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION insertcategory(categname character varying, parentid integer, source character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare 
  maxId int;
  num_rows int;
 begin
	select max("Id") + 1 from mhtc_sch.categories into maxId;

	if maxId is null then
	  maxId = 1;
	  end if;
	
	insert into mhtc_sch.categories ("Id", "Name","ParentId","Source")
	values (maxId,categName,parentid,source);

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$$;


ALTER FUNCTION mhtc_sch.insertcategory(categname character varying, parentid integer, source character varying) OWNER TO postgres;

--
-- Name: insertmetric(character varying, boolean, integer); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION insertmetric(metricname character varying, iscaclucated boolean, categoryid integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare 
  maxId int;
  num_rows int;
 begin
	select max("Id") + 1 from mhtc_sch.metrics into maxId;

	if maxId is null then
	  maxId = 1;
	  end if;

	insert into mhtc_sch.metrics ("Id","Name","IsCalculated")
	values (maxId,metricName,isCaclucated);

	insert into mhtc_sch.categoriesxmetrics
	values (categoryId,maxId);

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$$;


ALTER FUNCTION mhtc_sch.insertmetric(metricname character varying, iscaclucated boolean, categoryid integer) OWNER TO postgres;

--
-- Name: insertvalue(integer, integer, integer, double precision); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION insertvalue(stateid integer, metricid integer, year_val integer, val double precision) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare 
  num_rows int;
 begin

	insert into mhtc_sch.statistics ("StateId","MetricId","Year","Value")
	values (stateId,metricId,year_val,val);

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$$;


ALTER FUNCTION mhtc_sch.insertvalue(stateid integer, metricid integer, year_val integer, val double precision) OWNER TO postgres;

--
-- Name: updatevalue(integer, integer, integer, double precision); Type: FUNCTION; Schema: mhtc_sch; Owner: postgres
--

CREATE FUNCTION updatevalue(stateid integer, metricid integer, year_val integer, val double precision) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare 
  num_rows int;
 begin
	update mhtc_sch.statistics set "Value" = val
	where 	"MetricId" = metricId and
		"StateId" = stateid and
		"Year" = year_val;

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$$;


ALTER FUNCTION mhtc_sch.updatevalue(stateid integer, metricid integer, year_val integer, val double precision) OWNER TO postgres;

--
-- Name: categoriesxmetrics; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE categoriesxmetrics (
    "categoryId" integer NOT NULL,
    "metricId" integer NOT NULL
);


ALTER TABLE mhtc_sch.categoriesxmetrics OWNER TO postgres;

--
-- Name: deleted_statistics; Type: TABLE; Schema: mhtc_sch; Owner: ahoxha; Tablespace: 
--

CREATE TABLE deleted_statistics (
    "StateId" integer NOT NULL,
    "MetricId" integer NOT NULL,
    "Year" integer NOT NULL,
    "Value" double precision,
    "DateAdded" timestamp without time zone DEFAULT now()
);


ALTER TABLE mhtc_sch.deleted_statistics OWNER TO ahoxha;

--
-- Name: metrics; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE metrics (
    "Id" integer NOT NULL,
    "Name" character varying(256) NOT NULL,
    "Visible" boolean DEFAULT true NOT NULL,
    "IsCalculated" boolean DEFAULT false NOT NULL
);


ALTER TABLE mhtc_sch.metrics OWNER TO postgres;

--
-- Name: permissions; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE permissions (
    "Id" integer NOT NULL,
    "Permission" character varying(256) NOT NULL
);


ALTER TABLE mhtc_sch.permissions OWNER TO postgres;

--
-- Name: statistics; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE statistics (
    "StateId" integer NOT NULL,
    "MetricId" integer NOT NULL,
    "Year" integer NOT NULL,
    "Value" double precision,
    "DateAdded" timestamp without time zone DEFAULT now()
);


ALTER TABLE mhtc_sch.statistics OWNER TO postgres;

--
-- Name: usergroups; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE usergroups (
    "Id" integer NOT NULL,
    "Name" character varying(256) NOT NULL
);


ALTER TABLE mhtc_sch.usergroups OWNER TO postgres;

--
-- Name: usergroupsxpermissions; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE usergroupsxpermissions (
    "PermissionId" integer NOT NULL,
    "GroupId" integer NOT NULL
);


ALTER TABLE mhtc_sch.usergroupsxpermissions OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    "Id" integer NOT NULL,
    "Email" character varying(256) NOT NULL,
    "FirstName" character varying(25),
    "LastName" character varying(25),
    "PasswordHash" text NOT NULL,
    "IsApproved" boolean DEFAULT false NOT NULL,
    "GroupId" integer NOT NULL
);


ALTER TABLE mhtc_sch.users OWNER TO postgres;

--
-- Data for Name: categories; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY categories ("Id", "Name", "ParentId", "Visible", "Source") FROM stdin;
\.


--
-- Data for Name: categoriesxmetrics; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY categoriesxmetrics ("categoryId", "metricId") FROM stdin;
\.


--
-- Data for Name: deleted_statistics; Type: TABLE DATA; Schema: mhtc_sch; Owner: ahoxha
--

COPY deleted_statistics ("StateId", "MetricId", "Year", "Value", "DateAdded") FROM stdin;
\.


--
-- Data for Name: metrics; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY metrics ("Id", "Name", "Visible", "IsCalculated") FROM stdin;
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY permissions ("Id", "Permission") FROM stdin;
\.


--
-- Data for Name: states; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY states ("Id", "Abbreviation", "Name", "IsPeerState") FROM stdin;
1	AL	Alabama	f
2	AK	Alaska	f
3	AZ	Arizona	f
4	AR	Arkansas	f
7	CT	Connecticut	f
8	DE	Delaware	f
10	GA	Georgia	f
11	HI	Hawaii	f
12	ID	Idaho	f
13	IL	Illinois	f
14	IN	Indiana	f
15	IA	Iowa	f
16	KS	Kansas	f
17	KY	Kentucky	f
18	LA	Louisiana	f
20	MD	Maryland	f
22	MI	Michigan	f
23	MN	Minnesota	f
24	MS	Mississippi	f
25	MO	Missouri	f
26	MT	Montana	f
27	NE	Nebraska	f
29	NH	New Hampshire	f
30	NJ	New Jersey	f
31	NM	New Mexico	f
32	NY	New York	f
33	NC	North Carolina	f
34	ND	North Dakota	f
35	OH	Ohio	f
36	OK	Oklahoma	f
37	OR	Oregon	f
39	RI	Rhode Island	f
40	SC	South Carolina	f
41	SD	South Dakota	f
42	TN	Tennessee	f
44	UT	Utah	f
45	VT	Vermont	f
46	VA	Virginia	f
47	WA	Washington	f
48	WV	West Virginia	f
49	WI	Wisconsin	f
50	WY	Wyoming	f
51	US	United States	f
5	CA	California	t
6	CO	Colorado	t
9	FL	Florida	t
19	ME	Maine	t
21	MA	Massachusetts	t
28	NV	Nevada	t
38	PA	Pennsylvania	t
43	TX	Texas	t
\.


--
-- Data for Name: statistics; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY statistics ("StateId", "MetricId", "Year", "Value", "DateAdded") FROM stdin;
\.


--
-- Data for Name: usergroups; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY usergroups ("Id", "Name") FROM stdin;
\.


--
-- Data for Name: usergroupsxpermissions; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY usergroupsxpermissions ("PermissionId", "GroupId") FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: mhtc_sch; Owner: postgres
--

COPY users ("Id", "Email", "FirstName", "LastName", "PasswordHash", "IsApproved", "GroupId") FROM stdin;
\.


--
-- Name: C_unique; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT "C_unique" UNIQUE ("Email");


--
-- Name: PF; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metrics
    ADD CONSTRAINT "PF" PRIMARY KEY ("Id");


--
-- Name: PK; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT "PK" PRIMARY KEY ("Id");


--
-- Name: PK_P; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT "PK_P" PRIMARY KEY ("Id");


--
-- Name: PK_S; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY states
    ADD CONSTRAINT "PK_S" PRIMARY KEY ("Id");


--
-- Name: PK_groups; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usergroups
    ADD CONSTRAINT "PK_groups" PRIMARY KEY ("Id");


--
-- Name: PK_stat; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY statistics
    ADD CONSTRAINT "PK_stat" PRIMARY KEY ("StateId", "MetricId", "Year");


--
-- Name: PK_stat_d; Type: CONSTRAINT; Schema: mhtc_sch; Owner: ahoxha; Tablespace: 
--

ALTER TABLE ONLY deleted_statistics
    ADD CONSTRAINT "PK_stat_d" PRIMARY KEY ("StateId", "MetricId", "Year");


--
-- Name: PK_ugxperm; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usergroupsxpermissions
    ADD CONSTRAINT "PK_ugxperm" PRIMARY KEY ("PermissionId", "GroupId");


--
-- Name: PK_users; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT "PK_users" PRIMARY KEY ("Id");


--
-- Name: pk_1; Type: CONSTRAINT; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categoriesxmetrics
    ADD CONSTRAINT pk_1 PRIMARY KEY ("categoryId", "metricId");


--
-- Name: fki_FK; Type: INDEX; Schema: mhtc_sch; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_FK" ON categories USING btree ("ParentId");


--
-- Name: FK; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT "FK" FOREIGN KEY ("ParentId") REFERENCES categories("Id") ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: FK_group; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT "FK_group" FOREIGN KEY ("GroupId") REFERENCES usergroups("Id");


--
-- Name: FK_metrics; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY statistics
    ADD CONSTRAINT "FK_metrics" FOREIGN KEY ("MetricId") REFERENCES metrics("Id");


--
-- Name: FK_perm; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY usergroupsxpermissions
    ADD CONSTRAINT "FK_perm" FOREIGN KEY ("PermissionId") REFERENCES permissions("Id");


--
-- Name: FK_state; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY statistics
    ADD CONSTRAINT "FK_state" FOREIGN KEY ("StateId") REFERENCES states("Id");


--
-- Name: FK_ugroups; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY usergroupsxpermissions
    ADD CONSTRAINT "FK_ugroups" FOREIGN KEY ("GroupId") REFERENCES usergroups("Id");


--
-- Name: category_fk; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY categoriesxmetrics
    ADD CONSTRAINT category_fk FOREIGN KEY ("categoryId") REFERENCES categories("Id");


--
-- Name: metric_fk; Type: FK CONSTRAINT; Schema: mhtc_sch; Owner: postgres
--

ALTER TABLE ONLY categoriesxmetrics
    ADD CONSTRAINT metric_fk FOREIGN KEY ("metricId") REFERENCES metrics("Id");


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

