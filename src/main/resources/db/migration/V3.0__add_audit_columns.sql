-- noinspection SqlResolveForFile

ALTER TABLE COMPANY ADD COLUMN CREATED_AT DATE ;
ALTER TABLE COMPANY ADD COLUMN UPDATED_AT DATE ;
ALTER TABLE COMPANY ADD COLUMN CREATED_BY VARCHAR(255);
ALTER TABLE COMPANY ADD COLUMN MODIFIED_BY VARCHAR(255);