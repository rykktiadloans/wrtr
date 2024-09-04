DELETE FROM resources;
DELETE FROM posts;
DELETE FROM users;

INSERT INTO users
    (id, username, email, password, role)
    VALUES ('bd3c743f-32d1-44a9-989d-4bc6a3caa902', 'username', 'email', 'password', 'role');
INSERT INTO users
    (id, username, email, password, role)
    VALUES ('cfaadbbb-4f0d-4ef6-9ce3-bffcf874aad0', 'username-again', 'email2', 'password', 'role');
INSERT INTO posts
    (id, content, author_id, date)
    VALUES ('5e7389c1-2e11-40e0-b7e6-dc679676f9fe', 'content', 'bd3c743f-32d1-44a9-989d-4bc6a3caa902', DATE '2023-01-01');
INSERT INTO posts
    (id, content, author_id, date)
    VALUES ('bc19d892-f486-466b-8a46-6a9181b23e76', 'content2', 'bd3c743f-32d1-44a9-989d-4bc6a3caa902', DATE '2024-01-01');
INSERT INTO resources
    (id, post_id, path, name)
    VALUES('127b58ff-5c06-462d-bd87-dbd63a91bd32', '5e7389c1-2e11-40e0-b7e6-dc679676f9fe', '', 'name')
