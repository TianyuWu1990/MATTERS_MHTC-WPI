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

-- Function: mhtc_sch.insertcategory(character varying, integer, character varying)

-- DROP FUNCTION mhtc_sch.insertcategory(character varying, integer, character varying);

CREATE OR REPLACE FUNCTION mhtc_sch.insertcategory(categname character varying, parentid integer, source character varying, url character varying)
  RETURNS integer AS
$BODY$
declare 
  maxId int;
  num_rows int;
 begin
	select max("Id") + 1 from mhtc_sch.categories into maxId;

	if maxId is null then
	  maxId = 1;
	  end if;
	
	insert into mhtc_sch.categories ("Id", "Name","ParentId","Source","URL")
	values (maxId,categName,parentid,source,url);

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.insertcategory(character varying, integer, character varying)
  OWNER TO postgres;

-- Function: mhtc_sch.updatecategory(integer, character varying, boolean, character varying)

-- DROP FUNCTION mhtc_sch.updatecategory(integer, character varying, boolean, character varying);

CREATE OR REPLACE FUNCTION mhtc_sch.updatecategory(categoryid integer, cname character varying, visible boolean, source character varying, url character varying)
  RETURNS integer AS
$BODY$
declare 
  num_rows int;
 begin
	update mhtc_sch.categories set "Name" = cName, 
				    "Visible" = visible,
				    "Source" = source,
                    "URL" = url
	where 	"Id" = categoryId; 

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.updatecategory(integer, character varying, boolean, character varying, character varying)
  OWNER TO postgres;
