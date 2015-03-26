CREATE OR REPLACE FUNCTION mhtc_sch.merge_statistics(stateid integer, metricid integer, year_val integer, val double precision) RETURNS VOID AS
$$
BEGIN
    LOOP
        -- first try to update the statistic
        UPDATE mhtc_sch.statistics SET "Value" = val WHERE "StateId" = stateid AND "MetricId" = metricid AND "Year" = year_val;
        IF found THEN
            RETURN;
        END IF;

        -- not there, so try to insert the key
        -- if someone else inserts the same key concurrently,
        -- we could get unique-key failure (unlikely in our case)
        BEGIN
            PERFORM mhtc_sch.insertvalue(stateid, metricid, year_val, val);
            RETURN;
        EXCEPTION WHEN unique_violation THEN
            -- do nothing, and loop to try the UPDATE again
        END;
    END LOOP;
END;
$$
LANGUAGE plpgsql;
