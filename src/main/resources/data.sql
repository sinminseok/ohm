INSERT INTO MANAGER(MANAGER_ID, AGE, EMAIL, NAME, PASSWORD) VALUES (1, 23,  'admin@naver.com', 'admin','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi');



INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_MANAGER');


INSERT INTO ACCOUNT_AUTHORITY (MANAGER_ID, AUTHORITY_NAME) values (1, 'ROLE_MANAGER');