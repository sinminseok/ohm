
----Gym 등록
INSERT INTO GYM(GYM_ID, ADDRESS, AREA, CODE,COUNT, CURRENT_COUNT,HOLIDAY,INTRODUCE,NAME,ONELINE_INTRODUCE,TRAINER_COUNT,WEEKDAT_TIME,WEEKEND_TIME) VALUES (4,'오목로11길5', 30 , 0,1234,'케렌시아');
--
----manager 등록
----아이디 manager 비번 admin
--INSERT INTO MANAGER(MANAGER_ID, AGE, EMAIL, NAME, PASSWORD,GYM_ID) VALUES (1, 23,  'manager@naver.com', 'manager','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi',2);
--INSERT INTO MANAGER(MANAGER_ID, AGE, EMAIL, NAME, PASSWORD,GYM_ID) VALUES (2, 23,  'manager@naver.com', 'trainer','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi',2);
INSERT INTO CODE(CODE_ID,CODE) values (1,'1234');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_MANAGER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_TRAINER');

--INSERT INTO ACCOUNT_AUTHORITY (MANAGER_ID, AUTHORITY_NAME) values (1, 'ROLE_MANAGER');
--INSERT INTO ACCOUNT_AUTHORITY (MANAGER_ID, AUTHORITY_NAME) values (2, 'ROLE_TRAINER');





----Trainer 등록
--INSERT INTO TRAINER(TRAINER_ID, NAME , PASSWORD , PROFILE, SEX, GYM_ID) VALUES (5,'trainer','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','profile','MALE',2);

--POST 등록
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (1,'POST내용1','POST IMG','POST TITLE1',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (2,'POST내용2','POST IMG','POST TITLE2',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (3,'POST내용3','POST IMG','POST TITLE3',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (4,'POST내용4','POST IMG','POST TITLE4',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (5,'POST내5','POST IMG','POST TITLE5',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (6,'POST내용6','POST IMG','POST TITLE6',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (7,'POST내용','POST IMG','POST TITLE7',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (8,'POST내용','POST IMG','POST TITLE8',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (9,'POST내용','POST IMG','POST TITLE9',2);
--INSERT INTO POST(POST_ID, CONTENT, IMG, TITLE, GYM_ID) VALUES (10,'POST내용','POST IMG','POST TITLE10',2);
