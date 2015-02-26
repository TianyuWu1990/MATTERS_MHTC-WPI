-- Function: mhtc_sch.getmetricsbyparent(integer[])

DROP FUNCTION mhtc_sch.getmetricsbyparent(integer[]);

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
