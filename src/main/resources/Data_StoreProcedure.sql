--lấy danh sách bài viết theo userId để thêm vào trang chủ
create or ALTER PROCEDURE getPostData
    @user_id INT,
    @start_getter INT
AS
BEGIN
    SELECT
        p.post_id,
        p.content,
        p.image_url,
        p.post_time,
        p.post_like,
        u.user_id,
        u.full_name,
        u.url_avata,
        (
            SELECT COUNT(*)
            FROM [comment] AS c
            WHERE c.post_id = p.post_id
        ) AS amount_comment
		,(SELECT COUNT(*)
            FROM [user_like_post] AS ul
            WHERE ul.id_post_is_like = p.post_id)
			AS amount_like
			,(Select 1 
            FROM [user_like_post] AS ul
            WHERE ul.id_post_is_like = p.post_id and ul.id_user_like_post=@user_id)
			as islike
    FROM [post] AS p
    JOIN [user] AS u ON p.id_user_create = u.user_id
    WHERE (p.id_user_create IN (
            SELECT f.receiver_userid
            FROM [friend] AS f
            WHERE  f.is_friend = 1 and (f.receiver_userid = @user_id OR f.sender_userid = @user_id)
			UNION
			SELECT f.sender_userid
            FROM [friend] AS f
            WHERE  f.is_friend = 1 and (f.receiver_userid = @user_id OR f.sender_userid = @user_id)
        ))
    ORDER BY p.post_time DESC
    OFFSET @start_getter ROWS
    FETCH NEXT 7 ROWS ONLY;
END

exec getPostData 3,0


-- lấy thông tin cá nhân và số lường bài viết và số lượng bạn bè theo userId add vào trang thông tin tài khoản
create or alter proc getInforUser @userId int
as 
begin
select distinct u.user_id,u.full_name,u.url_avata,u.email ,
	(select count(*) from [post] as p where p.id_user_create=@userId) as amount_post,
	(select count(*) from [friend] as f where (f.receiver_userid=@userId or f.sender_userid=@userId) 
						and f.is_friend=1) as amount_friend
from [user] as u join [post] as p on p.id_user_create=u.user_id
where u.user_id=@userId 
end

exec getInforUser 7
select * from post as p where p.id_user_create=7
select * from [friend] as f where (f.receiver_userid=7 or f.sender_userid=7) 
						and f.is_friend=1


-- lấy danh sách bài viết theo userId add vào trang thông tin tài khoản
create or ALTER PROCEDURE getPostDataProfile
    @user_id INT,
    @start_getter INT
AS
BEGIN
    SELECT
        p.post_id,
        p.content,
        p.image_url,
        p.post_time,
        p.post_like,
        u.user_id,
         u.full_name,
        u.url_avata,
        (
            SELECT COUNT(*)
            FROM [comment] AS c
            WHERE c.post_id = p.post_id
        ) AS amount_comment
		,(SELECT COUNT(*)
            FROM [user_like_post] AS ul
            WHERE ul.id_post_is_like = p.post_id)
			AS amount_like
			,(Select 1 
            FROM [user_like_post] AS ul
            WHERE ul.id_post_is_like = p.post_id and ul.id_user_like_post=@user_id)
			as islike
    FROM [post] AS p
    JOIN [user] AS u ON p.id_user_create = u.user_id
    WHERE p.id_user_create =@user_id
    ORDER BY p.post_time DESC
    OFFSET @start_getter ROWS
    FETCH NEXT 7 ROWS ONLY;
END

exec getPostDataProfile 5,0


--lấy các comment 
create or alter proc getComment @post_id int , @start_getter int
as 
begin
	select 
		c.comment_id,
		c.parent_comment_id,
		c.content,
		c.comment_time,
		c.user_id,
		u.full_name,
		u.url_avata,
		(SELECT		COUNT(sub.comment_id) AS comment_count
			FROM [comment] cm
			LEFT JOIN [comment] sub ON cm.comment_id = sub.parent_comment_id
			where cm.comment_id=c.comment_id
			GROUP BY cm.comment_id)
		as amountCommentReply
	from [comment] as c join
	[user] as u on c.user_id=u.user_id
	where c.post_id=@post_id and c.parent_comment_id is null
	ORDER BY c.comment_time DESC
    OFFSET @start_getter ROWS
    FETCH NEXT 25 ROWS ONLY;
end

exec getComment 1,0

select cm.comment_id from [comment] cm where cm.parent_comment_id=2

update comment
set post_id=4
where parent_comment_id=4


--lấy danh sách lời mới kết bạn đã nhận

CREATE OR ALTER PROC getReceiverFriend
  @userId INT,
  @startGetter INT
AS
BEGIN
  SELECT
    f.friend_id,
    f.sender_userid,
    f.time_is_send,
    u.full_name AS userSenderFullName,
    u.url_avata
  FROM [friend] AS f
  JOIN [user] AS u ON f.sender_userid = u.user_id
  WHERE
    f.receiver_userid = @userId
    AND f.is_friend = 0
    AND f.is_hide = 0
	ORDER BY f.time_is_send DESC
    OFFSET @startGetter ROWS
    FETCH NEXT 20 ROWS ONLY;
END

exec getReceiverFriend 3,0
--drop PROC getReceiverFriend

--lấy danh sách lời mới kết bạn đã gui

CREATE OR ALTER PROC getSenderFriend
  @userId INT,
  @startGetter INT
AS
BEGIN
  SELECT
    f.friend_id,
    f.receiver_userid,
    f.time_is_send,
    u.full_name AS userReceiverFullName,
    u.url_avata
  FROM [friend] AS f
  JOIN [user] AS u ON f.receiver_userid = u.user_id
  WHERE
    f.sender_userid = @userId
    AND f.is_friend = 0
    AND f.is_hide = 0
	ORDER BY f.time_is_send DESC
    OFFSET @startGetter ROWS
    FETCH NEXT 20 ROWS ONLY;
END
exec getSenderFriend 1,0


create proc deletePost @post_id int
as 
begin
	begin
	delete from [comment]
	where comment.post_id=@post_id
	end
	begin
	delete from [post_readed]
	where post_readed.id_post_been_read=@post_id
	end
	delete from [post]
	where post.post_id=@post_id
end

exec deletePost 10


create or alter proc create_conversation @user_id_create int,@user_id_sercond int,


insert into [Role](role_id,role_name,permission)
values ('admin','admin',1),
('user','user',3)

INSERT INTO [user] (user_name, email, full_name, pass_word, role_id, url_avata)
VALUES
('user1', 'user1@example.com', 'Người dùng 1', 'password1', 'admin', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user2', 'user2@example.com', 'Người dùng 2', 'password2', 'admin', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user3', 'user3@example.com', 'Người dùng 3', 'password3', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user4', 'user4@example.com', 'Người dùng 4', 'password4', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user5', 'user5@example.com', 'Người dùng 5', 'password5', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user6', 'user6@example.com', 'Người dùng 6', 'password6', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user7', 'user7@example.com', 'Người dùng 7', 'password7', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user8', 'user8@example.com', 'Người dùng 8', 'password8', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user9', 'user9@example.com', 'Người dùng 9', 'password9', 'user', 'https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg'),
('user10', 'user10@example.com', 'Người dùng 10', 'password10', 'user','https://anhanime.com/wp-content/uploads/2022/05/A%CC%89nh-Anime-chibi-nam-7.jpg');



insert into [post] (id_user_create,post_like,post_time,image_url,[content])
values (1, 10, CURRENT_TIMESTAMP, 'https://cdn.vjshop.vn/tin-tuc/cach-chup-anh-phong-canh/cach-chup-anh-phong-canh-dep-15.jpg', N'Lập trình (tiếng Anh là Coding) - hoặc Lập trình máy tính - không chỉ là một số công việc khó hiểu được thực hiện bởi các lập trình viên siêu phàm thực hiện.'),
(2, 20, CURRENT_TIMESTAMP, 'https://example.com/image2.jpg', 'Nội dung bài đăng 2'),
(3, 30, CURRENT_TIMESTAMP, 'https://example.com/image3.jpg', 'Nội dung bài đăng 3'),
(4, 40, CURRENT_TIMESTAMP, 'https://example.com/image4.jpg', 'Nội dung bài đăng 4'),
(5, 50, CURRENT_TIMESTAMP, 'https://example.com/image5.jpg', 'Nội dung bài đăng 5'),
(6, 60, CURRENT_TIMESTAMP, 'https://example.com/image6.jpg', 'Nội dung bài đăng 6'),
(7, 70, CURRENT_TIMESTAMP, 'https://example.com/image7.jpg', 'Nội dung bài đăng 7'),
(8, 80, CURRENT_TIMESTAMP, 'https://example.com/image8.jpg', 'Nội dung bài đăng 8'),
(9, 90, CURRENT_TIMESTAMP, '', 'Nội dung bài đăng 9'),
(1, 100, CURRENT_TIMESTAMP, '', N'Hello mọi người '),
(1, 110, CURRENT_TIMESTAMP, 'https://cdn.vjshop.vn/tin-tuc/cach-chup-anh-phong-canh/cach-chup-anh-phong-canh-dep-15.jpg', 'Nội dung bài đăng 11'),
(2, 120, CURRENT_TIMESTAMP, 'https://cdn.vjshop.vn/tin-tuc/cach-chup-anh-phong-canh/cach-chup-anh-phong-canh-dep-15.jpg', 'Nội dung bài đăng 12'),
(1, 130, CURRENT_TIMESTAMP, 'https://example.com/image13.jpg', 'Nội dung bài đăng 13'),
(4, 140, CURRENT_TIMESTAMP, 'https://example.com/image14.jpg', 'Nội dung bài đăng 14'),
(4, 150, CURRENT_TIMESTAMP, 'https://example.com/image15.jpg', 'Nội dung bài đăng 15'),
(6, 160, CURRENT_TIMESTAMP, 'https://example.com/image16.jpg', 'Nội dung bài đăng 16'),
(7, 170, CURRENT_TIMESTAMP, '', 'Nội dung bài đăng 17'),
(9, 180, CURRENT_TIMESTAMP, 'https://cdn.vjshop.vn/tin-tuc/cach-chup-anh-phong-canh/cach-chup-anh-phong-canh-dep-15.jpg', 'Nội dung bài đăng 18'),
(1, 190, CURRENT_TIMESTAMP, '', 'Nội dung bài đăng 19'),
(2, 200, CURRENT_TIMESTAMP, 'https://example.com/image20.jpg', 'Nội dung bài đăng 20');

INSERT INTO [comment] (post_id, user_id, comment_time, [content])
VALUES
( 1, 1, '2023-10-15 12:50:00', 'Nội dung bình luận 11'),
( 2, 2, '2023-10-15 12:55:00', 'Nội dung bình luận 12'),
( 3, 3, '2023-10-15 13:00:00', 'Nội dung bình luận 13'),
( 4, 4, '2023-10-15 13:05:00', 'Nội dung bình luận 14'),
( 5, 5, '2023-10-15 13:10:00', 'Nội dung bình luận 15'),
( 6, 6, '2023-10-15 13:15:00', 'Nội dung bình luận 16'),
( 7, 7, '2023-10-15 13:20:00', 'Nội dung bình luận 17'),
( 8, 8, '2023-10-15 13:25:00', 'Nội dung bình luận 18')


INSERT INTO [comment] (parent_comment_id, post_id, user_id, comment_time, [content])
VALUES
(null, 1, 1, '2023-10-15 12:00:00', 'Nội dung bình luận 1'),
(2, 2, 2, '2023-10-15 12:05:00', 'Nội dung bình luận 2'),
(1, 3, 3, '2023-10-15 12:10:00', 'Nội dung bình luận 3'),
(3, 4, 4, '2023-10-15 12:15:00', 'Nội dung bình luận 4'),
(2, 5, 5, '2023-10-15 12:20:00', 'Nội dung bình luận 5'),
(4, 6, 6, '2023-10-15 12:25:00', 'Nội dung bình luận 6'),
(3, 7, 7, '2023-10-15 12:30:00', 'Nội dung bình luận 7'),
(2, 8, 8, '2023-10-15 12:35:00', 'Nội dung bình luận 8'),
(4, 9, 9, '2023-10-15 12:40:00', 'Nội dung bình luận 9'),
(1, 10, 10, '2023-10-15 12:45:00', 'Nội dung bình luận 10'),
(4, 9, 9, '2023-10-15 13:30:00', 'Nội dung bình luận 19'),
(1, 10, 10, '2023-10-15 13:35:00', 'Nội dung bình luận 20'),
(1, 1, 1, '2023-10-15 13:40:00', 'Nội dung bình luận 21'),
(2, 2, 2, '2023-10-15 13:45:00', 'Nội dung bình luận 22'),
(1, 3, 3, '2023-10-15 13:50:00', 'Nội dung bình luận 23'),
(3, 4, 4, '2023-10-15 13:55:00', 'Nội dung bình luận 24'),
(2, 5, 5, '2023-10-15 14:00:00', 'Nội dung bình luận 25'),
(4, 6, 6, '2023-10-15 14:05:00', 'Nội dung bình luận 26'),
(3, 7, 7, '2023-10-15 14:10:00', 'Nội dung bình luận 27'),
(2, 8, 8, '2023-10-15 14:15:00', 'Nội dung bình luận 28'),
(4, 9, 9, '2023-10-15 14:20:00', 'Nội dung bình luận 29'),
(1, 10, 10, '2023-10-15 14:25:00', 'Nội dung bình luận 30'),
(1, 1, 1, '2023-10-15 14:30:00', 'Nội dung bình luận 31'),
(2, 2, 2, '2023-10-15 14:35:00', 'Nội dung bình luận 32'),
(1, 3, 3, '2023-10-15 14:40:00', 'Nội dung bình luận 33'),
(3, 4, 4, '2023-10-15 14:45:00', 'Nội dung bình luận 34'),
(2, 5, 5, '2023-10-15 14:50:00', 'Nội dung bình luận 35'),
(4, 6, 6, '2023-10-15 14:55:00', 'Nội dung bình luận 36'),
(3, 7, 7, '2023-10-15 15:00:00', 'Nội dung bình luận 37'),
(2, 8, 8, '2023-10-15 15:05:00', 'Nội dung bình luận 38'),
(4, 9, 9, '2023-10-15 15:10:00', 'Nội dung bình luận 39');
INSERT INTO [friend] (is_friend, is_pending, receiver_userid, sender_userid, time_is_send)
VALUES
(1, 0, 1, 2, CURRENT_TIMESTAMP),
(1, 0, 2, 3, CURRENT_TIMESTAMP),
(0, 0, 3, 4, CURRENT_TIMESTAMP),
(0, 0, 4, 5, CURRENT_TIMESTAMP),
(1, 0, 5, 6, CURRENT_TIMESTAMP),
(1, 0, 6, 7, CURRENT_TIMESTAMP),
(0, 0, 7, 8, CURRENT_TIMESTAMP),
(0, 0, 8, 9, CURRENT_TIMESTAMP),
(1, 0, 9, 10, CURRENT_TIMESTAMP),
(0, 0, 10, 1, CURRENT_TIMESTAMP),
(1, 0, 1, 3, CURRENT_TIMESTAMP),
(0, 0, 3, 5, CURRENT_TIMESTAMP),
(1, 0, 5, 7, CURRENT_TIMESTAMP),
(0, 0, 7, 9, CURRENT_TIMESTAMP),
(1, 0, 9, 1, CURRENT_TIMESTAMP),
(1, 0, 1, 4, CURRENT_TIMESTAMP),
(1, 0, 4, 6, CURRENT_TIMESTAMP),
(1, 0, 6, 8, CURRENT_TIMESTAMP),
(0, 0, 8, 10, CURRENT_TIMESTAMP);


INSERT INTO [user_like_post] (id_post_is_like, id_user_like_post)
VALUES
   (1, 1),(2, 2),(3, 3),(4, 4),(5, 5),(6, 6),(7, 7),(8, 8),(9, 9),
   (10, 10),(11, 1),(12, 2),(13, 3),(14, 4),(15, 5),(16, 6),(17, 7),(18, 8),(19, 9),(20, 10),(1, 2),(2, 3),(3, 4),(4, 5),(5, 6),
   (10, 1),(11, 2),(12, 3),(13, 4),(1, 3),(7, 9),(8, 10);
