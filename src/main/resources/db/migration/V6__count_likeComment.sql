CREATE OR REPLACE FUNCTION comment_like_count_trigger_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
UPDATE comment
SET like_count = (SELECT count(*) AS box_rate
                  FROM comment_like
                  WHERE comment.article_id = NEW.article_id and status = 'LIKE')
WHERE id = NEW.article_id;
RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER comment_like_count_trigger
    AFTER INSERT OR UPDATE OR DELETE
                    ON comment_like
                        FOR EACH ROW
                        EXECUTE FUNCTION comment_like_count_trigger_function()