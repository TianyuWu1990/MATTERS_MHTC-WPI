DROP SEQUENCE IF EXISTS mhtc_sch.id CASCADE;

DROP TABLE IF EXISTS mhtc_sch.manual_upload;
CREATE SEQUENCE mhtc_sch.id;

CREATE TABLE mhtc_sch."manual_upload" (
    id integer DEFAULT nextval('mhtc_sch.id'::regclass) NOT NULL,
    parentcategory text NOT NULL,
    subcategory text NOT NULL,
    metric text,
    dateadded timestamp without time zone DEFAULT now() NOT NULL,
    filename text NOT NULL,
    path text NOT NULL,
    CONSTRAINT upload_pkey PRIMARY KEY (id)
);
