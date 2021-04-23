create or replace function updateStatistic(inout urlId bigint)
as
$$
update statistics
set total_calls = total_calls + 1
where url_id = urlId
returning urlId;
$$
    language sql;