create procedure updateStatistic(INOUT urlId bigint)
as
$$
update statistics
SET total_calls = total_calls + 1
where url_id = urlId returning url_id;
$$
    language sql;