-- Table: mhtc_sch.pipelines

-- DROP TABLE mhtc_sch.pipelines;

CREATE TABLE mhtc_sch.pipelines
(
  pipelinename text NOT NULL,
  pipelinedesc text,
  path text NOT NULL,
  filename text NOT NULL,
  dateadded timestamp without time zone DEFAULT now(),
  uploadedby text NOT NULL,
  CONSTRAINT pipelines_pkey PRIMARY KEY (pipelinename)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mhtc_sch.pipelines
  OWNER TO postgres;
