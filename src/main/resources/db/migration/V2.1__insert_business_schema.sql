INSERT INTO COMPANY(ID, NAME) VALUES (1, 'Pepsi');
INSERT INTO COMPANY(ID, NAME) VALUES (2, 'Coca Cola');
INSERT INTO COMPANY(ID, NAME) VALUES (3, 'Sprite');


INSERT INTO CAR(ID, REGISTRATION_NUMBER, COMPANY_ID) VALUES (1, 'XYZ10ABC', 1);
INSERT INTO CAR(ID, REGISTRATION_NUMBER, COMPANY_ID) VALUES (2, 'XYZ11ABC', 1);
INSERT INTO CAR(ID, REGISTRATION_NUMBER, COMPANY_ID) VALUES (3, 'XYZ12ABC', 1);
INSERT INTO CAR(ID, REGISTRATION_NUMBER, COMPANY_ID) VALUES (4, 'XYZ13ABC', 2);


INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (1, 1, 'Street X', '12-341');
INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (2, 2, 'Street Y', '12-342');
INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (3, 3, 'Street Z', '12-343');
INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (4, 4, 'Street XX', '12-344');
INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (5, 5, 'Street YY', '12-345');
INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (6, 6, 'Street ZZ', '12-346');
INSERT INTO ADDRESS(ID, HOUSE_NUMBER, STREET, ZIP_CODE) VALUES (7, 7, 'Street XXX', '12-347');

INSERT INTO DEPARTMENT(ID, NAME, COMPANY_ID) VALUES (1, 'Sales & Marketing', 1);
INSERT INTO DEPARTMENT(ID, NAME, COMPANY_ID) VALUES (2, 'Research & Development', 1);
INSERT INTO DEPARTMENT(ID, NAME, COMPANY_ID) VALUES (3, 'Administration', 1);
INSERT INTO DEPARTMENT(ID, NAME, COMPANY_ID) VALUES (4, 'Human Resources', 2);
INSERT INTO DEPARTMENT(ID, NAME, COMPANY_ID) VALUES (5, 'Sales & Marketing', 3);

INSERT INTO EMPLOYEE(ID, NAME, SURNAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (1, 'John', 'William', 1, 1);
INSERT INTO EMPLOYEE(ID, NAME, SURNAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (2, 'Robert', 'James', 2, 2);
INSERT INTO EMPLOYEE(ID, NAME, SURNAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (3, 'Donald', 'Tyler', 3, 3);

INSERT INTO OFFICE(ID, NAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (1, 'OfficeEntity of S&M Boston', 4, 1);
INSERT INTO OFFICE(ID, NAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (2, 'OfficeEntity of S&M New York', 5, 1);
INSERT INTO OFFICE(ID, NAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (3, 'OfficeEntity of R&D Boston', 6, 2);
INSERT INTO OFFICE(ID, NAME, ADDRESS_ID, DEPARTMENT_ID) VALUES (4, 'OfficeEntity of A Los Angeles', 7, 3);