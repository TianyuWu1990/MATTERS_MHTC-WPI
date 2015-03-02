-- Function: mhtc_sch.getmetricsbyparent(integer[])

-- DROP FUNCTION mhtc_sch.getmetricsbyparent(integer[]);

CREATE OR REPLACE FUNCTION mhtc_sch.getmetricsbyparent(IN parentids integer[] DEFAULT '{}'::integer[])
  RETURNS TABLE("Id" integer, "Name" character varying, "Visible" boolean, "IsCalculated" boolean, "DataType" character varying, 
  "DisplayName" character varying, "URL" character varying, "Source" character varying, "TrendType" character varying, "ParentId" integer) AS
$BODY$
BEGIN
IF parentids <> '{}'::int[] THEN
	return QUERY
	select	m."Id",
		m."Name",
		m."Visible",
		m."IsCalculated",
		m."DataType",
		m."DisplayName",
		m."URL",
		m."Source",
		m."TrendType",
		c."ParentId"
	from mhtc_sch.metrics m
		inner join mhtc_sch.categoriesxmetrics cxm on m."Id" = cxm."metricId"
		inner join mhtc_sch.categories c on cxm."categoryId" = c."Id"
	where c."ParentId" = ANY(parentids)
		and m."Visible" = true;
	
 end if;
 END; 
$BODY$
  LANGUAGE plpgsql IMMUTABLE
  COST 100
  ROWS 1000;
ALTER FUNCTION mhtc_sch.getmetricsbyparent(integer[])
  OWNER TO postgres;

-- Function: mhtc_sch.getdatabycategory(integer)

-- DROP FUNCTION mhtc_sch.getdatabycategory(integer);

CREATE OR REPLACE FUNCTION mhtc_sch.getdatabycategory(IN categoryid integer)
  RETURNS TABLE("MetricName" character varying, "StateName" character varying, "Year" integer, "Value" double precision) AS
$BODY$
BEGIN

    RETURN QUERY
    SELECT m."Name" MetricName,
            st."Name" StateName,
            s."Year",
            s."Value"
    FROM mhtc_sch.statistics s
    INNER JOIN mhtc_sch.states st ON s."StateId" = st."Id"
    INNER JOIN mhtc_sch.categoriesxmetrics cxm ON cxm."metricId" = s."MetricId"
    INNER JOIN mhtc_sch.metrics m ON m."Id" = s."MetricId"
    WHERE cxm."categoryId" = categoryid;
END;
$BODY$
  LANGUAGE plpgsql IMMUTABLE
  COST 100
  ROWS 1000;
ALTER FUNCTION mhtc_sch.getdatabycategory(integer)
  OWNER TO postgres;

-- Function: mhtc_sch.insertmetric(character varying, boolean, integer, character varying)

-- DROP FUNCTION mhtc_sch.insertmetric(character varying, boolean, integer, character varying);

CREATE OR REPLACE FUNCTION mhtc_sch.insertmetric(metricname character varying, iscalculated boolean, categoryid integer, datatype character varying)
  RETURNS integer AS
$BODY$
declare 
  maxId int;
  num_rows int;
 begin
	select max("Id") + 1 from mhtc_sch.metrics into maxId;

	if maxId is null then
	  maxId = 1;
	  end if;

	insert into mhtc_sch.metrics ("Id","Name","IsCalculated", "DataType")
	values (maxId, metricname, iscalculated, datatype);

	insert into mhtc_sch.categoriesxmetrics
	values (categoryid, maxId);

	GET DIAGNOSTICS num_rows = ROW_COUNT;

	return num_rows;
 end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION mhtc_sch.insertmetric(character varying, boolean, integer, character varying)
  OWNER TO postgres;
