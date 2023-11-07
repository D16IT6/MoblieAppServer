create database MobileApp
drop database MobileApp
go 
use MobileApp
create table [role](
role_id varchar(30) primary key,
role_name varchar(30),
permission int,
)
create table [User](
userId int primary key,
userName varchar(30),
[passWord] varchar(30),
fullName Nvarchar(50),
urlAvata nvarchar(100),
email nvarchar(50),
roleId varchar(30) foreign key(roleId)references [Role](roleId),
)

create table Post(
userId int foreign key(userId)references [User](userId),
postId int primary key,
content Nvarchar(255),
imageUrl nvarchar(100),
postTime DateTime,
postLike int,
)

create table Comment(
commentId int primary key,
userId int foreign key(userId)references [User](userId),
postId int foreign key (postId) references Post(postId),
content varchar(Max),
commentTime datetime,
parentCommentId int foreign key(parentCommentId) references Comment(commentId),  
)
create table Friend (
	friendId int primary key,
	SenderUserID int foreign key(SenderUserID) REFERENCES [User](UserID),
    ReceiverUserID int foreign key (ReceiverUserID)REFERENCES [User](UserID),
	IsFriend BIT,
    IsPending BIT,
	timeIsSend datetime,
)


create table Conversations(
 conversationsId int primary key,
 conversationsName Nvarchar(100),
 [type] nvarchar(30),
 creatorUserID int foreign key(creatorUserId)references [User](userId)
)
create table Participants(
ParticipantID int PRIMARY KEY,
    conversationsId int REFERENCES Conversations(conversationsId),
    UserID int foreign key (UserID) REFERENCES [User](UserID),
    Role VARCHAR(10),
    NicknameUser NVARCHAR(50)
)
create table [Message](
	messageId int primary key,
	conversationsId int foreign key (conversationsId) references Conversations(conversationsId),
	content varchar(max),
	SenderUserID int foreign key (SenderUserID) references Participants(ParticipantID),
	[Timestamp] datetime,
)

create table [user] (user_id int identity not null,
email varchar(50), full_name varchar(50), pass_word varchar(30),
url_avata varchar(100), user_name varchar(30), role_id varchar(30),
primary key (user_id))

select '*' from [user]


select p.post_id,p.content,p.image_url,p.post_time,p.post_like,p.id_user_create from [post] as p,[user] as u

select f.receiver_userid from [friend] as f
where f.is_friend=1

alter proc getPostData @user_id int, @start_getter int
as 
begin 
	select p.post_id,p.content,p.image_url,p.post_time,p.post_like,p.id_user_create from [post] as p,[user] as u
	where p.id_user_create=(select f.receiver_userid from [friend] as f
							where (f.receiver_userid=@user_id or f.sender_userid=@user_id) and f.is_friend=1)
			and p.post_id not in (select pr.id_post_been_read from [post_readed] as pr)
	order by  p.post_time desc
	OFFSET @start_getter ROWS
	FETCH NEXT 5 ROWS ONLY;
end

exec getPostData 123,5





