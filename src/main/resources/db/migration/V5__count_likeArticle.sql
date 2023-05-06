CREATE OR REPLACE FUNCTION article_like_count_trigger_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
UPDATE article
SET like_count = (SELECT count(*) AS like
                  FROM article_like
                  WHERE article_id = NEW.article_id and article.status = 'LIKE')
WHERE id = NEW.article_id;
RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER article_like_count_trigger
    AFTER INSERT OR UPDATE OR DELETE
                    ON article_like
                        FOR EACH ROW
                        EXECUTE FUNCTION article_like_count_trigger_function();