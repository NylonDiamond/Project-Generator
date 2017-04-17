-- database name
CREATE DATABASE projectdatabase;

\c projectdatabase;

-- main tables
CREATE TABLE project(project_id SERIAL PRIMARY KEY, project_title text, project_description text);
CREATE TABLE tag(tag_id SERIAL PRIMARY KEY, tag_title text);

-- Linking table
CREATE TABLE project_tag(project_id bigint, tag_id bigint);

INSERT INTO project(project_title, project_description) VALUES ('Code Clock','Bla Bla Bla, figure it out.'), ('Basic Blog','Create a basic blog that allows content creation, easy editing, and a functioning comment system');

INSERT INTO tag(tag_title) Values ('Java'),('C#'),('Aurelia'),('Javascript'),('CSS'),('HTML'),('C'),('C++'),('Cobal'),('Punch Card'),('SQL');

INSERT INTO project_tag(project_id, tag_id) Values (1,4),(1,5),(1,6),(2,4),(2,5),(2,6),(2,11);
