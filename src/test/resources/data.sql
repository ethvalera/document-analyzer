-- Insert teams
INSERT INTO team (id, name) VALUES
(100, 'Engineering'),
(200, 'Support'),
(300, 'Marketing'),
(400, 'HR');

-- Insert users
INSERT INTO user_account (id, email, created_at) VALUES
(100, 'user1@example.com', '2024-03-01 10:00:00+00'),  -- Before period, has doc in period (active)
(200, 'user2@example.com', '2024-04-01 12:00:00+00'),  -- Before period, no docs (inactive)
(300, 'user3@example.com', '2024-02-01 14:00:00+00'),  -- Before period, doc outside period (inactive)
(400, 'user4@example.com', '2024-01-15 09:30:00+00'),  -- Before period, multiple docs in/out period (active)
(500, 'user5@example.com', '2024-05-10 16:00:00+00'),  -- After period start, no docs (inactive if period adjusted)
(600, 'user6@example.com', '2024-03-20 11:15:00+00');  -- Before period, doc before period (inactive)

-- Link users to teams
INSERT INTO user_team (user_id, team_id) VALUES
(100, 100),  -- User 1 in Engineering
(200, 200),  -- User 2 in Support
(300, 100),  -- User 3 in Engineering
(400, 300),  -- User 4 in Marketing
(400, 400),  -- User 4 in HR
(500, 200),  -- User 5 in Support
(600, 300);  -- User 6 in Marketing

-- Insert documents
INSERT INTO document (id, name, word_count, uploaded_at, user_id) VALUES
(100, 'doc1.txt', 500, '2024-04-10 09:00:00+00', 100),  -- In period (User 1)
(200, 'doc2.txt', 800, '2024-06-15 15:00:00+00', 300),  -- After period (User 3)
(300, 'doc3.txt', 1200, '2024-04-20 14:00:00+00', 400), -- In period (User 4)
(400, 'doc4.txt', 600, '2024-03-01 08:00:00+00', 400),  -- Before period (User 4)
(500, 'doc5.txt', 900, '2024-07-01 10:30:00+00', 400),  -- After period (User 4)
(600, 'doc6.txt', 300, '2024-03-15 13:00:00+00', 600),  -- Before period (User 6)
(700, 'doc7.txt', 1500, '2024-02-01 12:00:00+00', 100), -- Before period (User 1)
(800, 'doc8.txt', 400, '2024-05-05 16:00:00+00', 300); -- After period (User 3)