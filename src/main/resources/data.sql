--  insert category
insert into category(name_uz, name_ru, name_eng) values ('name_uz', 'name_ru', 'name_eng');
-- insert admin
insert into profile(name,surname,password,email,role,created_date)
SELECT 'TOshmat','Toshatov','123','toshmat@mail.ru','ADMIN',now()
    WHERE
    NOT EXISTS (
        SELECT id FROM profile WHERE surname = 'Toshatov'
    );