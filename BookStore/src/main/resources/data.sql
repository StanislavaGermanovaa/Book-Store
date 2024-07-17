-- user roles
INSERT INTO roles (id, role)
VALUES (1, 'ADMIN');
INSERT INTO roles (id, role)
VALUES (2, 'USER');

--users

INSERT INTO users(id, age, email, full_name, password, username)
VALUES (1, 35, 'admin@admin.com', 'Admin Adminov','$2a$10$w2a27lam.XD6oykAT0TaquZYhqDEAmVbROpmACvL/cD2OJi1qrvaW','ADMIN');
INSERT INTO users(id, age, email, full_name, password, username)
VALUES (2, 20, 'user@user.com', 'User Userov','$2a$10$8Lj.33RVpwyHqiIKWUZsIu33U27ZgjLfcIWd9k7.1QTzbXD.jF.JO','USER');

-- user roles
-- admin
INSERT INTO users_roles (`users_id`, `roles_id`)
VALUES (1, 1);
INSERT INTO users_roles (`users_id`, `roles_id`)
VALUES (1, 2);

-- user
INSERT INTO users_roles (`users_id`, `roles_id`)
VALUES (2, 2);

--category
INSERT INTO categories(id,name,description)
VALUES (1,'CRIME_STORY','Crime story is term used to describe narratives that centre on criminal acts and especially on the investigation, either by an amateur or a professional detective, of a crime, often a murder.');
INSERT INTO categories(id,name,description)
VALUES (2,'HORROR','Horror stories are designed to evoke fear, fascination, or revulsion in the reader. This is done either through supernatural elements or psychological circumstances. Sometimes horror is also considered dark fantasy because the laws of nature must be violated in some way, qualifying the story as fantastic.');
INSERT INTO categories(id,name,description)
VALUES (3,'THRILLER','Thriller is a genre of fiction with numerous, often overlapping, subgenres, including crime, horror, and detective fiction. Thrillers are characterized and defined by the moods they elicit, giving their audiences heightened feelings of suspense, excitement, surprise, anticipation and anxiety.');
INSERT INTO categories(id,name,description)
VALUES (4,'CLASSIC','A classic is a book accepted as being exemplary or particularly noteworthy.');
INSERT INTO categories(id,name,description)
VALUES (5,'LOVE_STORY','A love story is a narrative centered around the progression of two characters'' relationship as they deal with internal and external obstacles to be together.');
INSERT INTO categories(id,name,description)
VALUES (6,'SCIENCE_FICTION','Science fiction, also often known as ''sci-fi'', is a genre of literature that is imaginative and based around science. It relies heavily on scientific facts, theories, and principles as support for its settings, characters, themes, and plot.');
INSERT INTO categories(id,name,description)
VALUES (7,'FANTASY','Fantasy literature is literature set in an imaginary universe, often but not always without any locations, events, or people from the real world. Magic, the supernatural and magical creatures are common in many of these imaginary worlds. Fantasy literature may be directed at both children and adults.');
INSERT INTO categories(id,name,description)
VALUES (8,'FAIRY_TALE','A fairytale is a Genre of magical story, usually originating in folklore. Typically in European fairy tales, a poor, brave, and resourceful hero or heroine goes through testing adventures to eventual good fortune.');

--authors
INSERT INTO authors(id,name)
VALUES (1,'Ashley Elston');
INSERT INTO authors(id,name)
VALUES (2,'Rebecca Yarros');
INSERT INTO authors(id,name)
VALUES (3,'Catherine Newman');
INSERT INTO authors(id,name)
VALUES (4,'Elin Hilderbrand');
INSERT INTO authors(id,name)
VALUES (5,'Christina Lauren');
INSERT INTO authors(id,name)
VALUES (6,'Freida McFadden');
INSERT INTO authors(id,name)
VALUES (7,'Carley Fortune');

 --books
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES(1,'“This fast-paced read has everything you could want in a thriller: secret identities, a mysterious boss and a cat & mouse game that kept me guessing the whole way through.” —Reese Witherspoon

Evie Porter has everything a nice Southern girl could want: a doting boyfriend, a house with a white picket fence, a tight group of friends. The only catch: Evie Porter doesn’t exist.

The identity comes first: Evie Porter. Once she’s given a name and location by her mysterious boss, Mr. Smith, she learns everything there is to know about the town and the people in it. Then the mark: Ryan Sumner. The last piece of the puzzle is the job.

Evie isn’t privy to Mr. Smith’s real identity, but she knows this job isn’t like the others. Ryan has gotten under her skin, and she’s starting to envision a different sort of life for herself. But Evie can’t make any mistakes—especially after what happened last time.

Evie Porter must stay one step ahead of her past while making sure there’s still a future in front of her. The stakes couldn’t be higher—but then, Evie has always liked a challenge. . . .',
'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721226518/g5cbny2zhj1ulkhec2fh.jpg',15.00,'First Lie Wins',1,3);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (2,'“The first year is when some of us lose our lives. The second year is when the rest of us lose our humanity.” —Xaden Riorson

Everyone expected Violet Sorrengail to die during her first year at Basgiath War College—Violet included. But Threshing was only the first impossible test meant to weed out the weak-willed, the unworthy, and the unlucky.

Now the real training begins, and Violet’s already wondering how she’ll get through. It’s not just that it’s grueling and maliciously brutal, or even that it’s designed to stretch the riders’ capacity for pain beyond endurance. It’s the new vice commandant, who’s made it his personal mission to teach Violet exactly how powerless she is–unless she betrays the man she loves.

Although Violet’s body might be weaker and frailer than everyone else’s, she still has her wits—and a will of iron. And leadership is forgetting the most important lesson Basgiath has taught her: Dragon riders make their own rules.

But a determination to survive won’t be enough this year.

Because Violet knows the real secret hidden for centuries at Basgiath War College—and nothing, not even dragon fire, may be enough to save them in the end.',
'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721227828/z1peczbufgk7mtzyv8t6.jpg',
        25.00,'Iron Flame',2,7);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (3,'“Sandwich is joy in book form. I laughed continuously, except for the parts that made me cry. Catherine Newman does a miraculous job reminding us of all the wonder there is to be found in life."—Ann Patchett, New York Times bestselling author of Tom Lake

“If you like my novels, you will love love love this . . . . I stand in awe, it’s just perfect.”—Elin Hilderbrand, #1 New York Times bestselling author of Swan Song

From the beloved author of We All Want Impossible Things, a moving, hilarious story of a family summer vacation full of secrets, lunch, and learning to let go.

For the past two decades, Rocky has looked forward to her family’s yearly escape to Cape Cod. Their humble beach-town rental has been the site of sweet memories, sunny days, great meals, and messes of all kinds: emotional, marital, and—thanks to the cottage’s ancient plumbing—septic too.

This year’s vacation, with Rocky sandwiched between her half-grown kids and fully aging parents, promises to be just as delightful as summers past—except, perhaps, for Rocky’s hormonal bouts of rage and melancholy. (Hello, menopause!) Her body is changing—her life is, too. And then a chain of events sends Rocky into the past, reliving both the tenderness and sorrow of a handful of long-ago summers.

It’s one precious week: everything is in balance; everything is in flux. And when Rocky comes face to face with her family’s history and future, she is forced to accept that she can no longer hide her secrets from the people she loves.',
'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721228231/rnp7ic9nlotwp7puxfp6.jpg',
           15.00,'Sandwich',3,7);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (4,'The beloved #1 New York Times bestselling author brings her Nantucket novels to a brilliant finish: when rich strangers move to the island, social mayhem—and a possible murder follow. Can Nantucket’s best locals save the day, and their way of life?

Chief of Police Ed Kapenash is about to retire. Blond Sharon is going through a divorce. But when a 22-million-dollar summer home is purchased by the mysterious Richardsons—how did they make their money, exactly?—Ed, Sharon, and everyone in the community are swept up in high drama. The Richardsons throw lavish parties, flirt with multiple locals, flaunt their wealth with not one but two yachts, and raise impossible hopes of everyone they meet. When their house burns to the ground and their most essential employee goes missing, the entire island is up in arms.

The last of Elin Hilderbrand’s bestselling Nantucket novels, Swan Song is a propulsive medley of glittering gatherings, sun-soaked drama, wisdom and heart, featuring the return of some of her most beloved characters, including, most importantly, the beautiful and timeless island of Nantucket itself.',
'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721229210/f8aaqxmpfes1bsjqwi0k.jpg',
           29.90,'Swan Song',4,5);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (5,'Christina Lauren, returns with a delicious new romance between the buttoned-up heir of a grocery chain and his free-spirited artist ex as they fake their relationship in order to receive a massive inheritance.

Anna Green thought she was marrying Liam “West” Weston for access to subsidized family housing while at UCLA. She also thought she’d signed divorce papers when the graduation caps were tossed, and they both went on their merry ways.',
        'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721229534/lyqgxdb5q9yh9vfh7can.jpg',
           19.90,'The Paradise Problem',5,5);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (6,'Lesson #1: Trust no one.',
        'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721229678/az7kcscimctomikhj5vg.jpg',
           24.90,'The teacher',6,3);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (7,'This summer they’ll keep their promise. This summer they won’t give into temptation. This summer will be different.

Lucy is the tourist vacationing at a beach house on Prince Edward Island. Felix is the local who shows her a very good time. The only problem: Lucy doesn’t know he’s her best friend’s younger brother. Lucy and Felix’s chemistry is unreal, but the list of reasons why they need to stay away from each other is long, and they vow to never repeat that electric night again.

It’s easier said than done.

Each year, Lucy escapes to PEI for a big breath of coastal air, fresh oysters and crisp vinho verde with her best friend, Bridget. Every visit begins with a long walk on the beach, beneath soaring red cliffs and a golden sun. And every visit, Lucy promises herself she won’t wind up in Felix’s bed. Again.

If Lucy can’t help being drawn to Felix, at least she’s always kept her heart out of it.

When Bridget suddenly flees Toronto a week before her wedding, Lucy drops everything to follow her to the island. Her mission is to help Bridget through her crisis and resist the one man she’s never been able to. But Felix’s sparkling eyes and flirty quips have been replaced with something new, and Lucy’s beginning to wonder just how safe her heart truly is.',
        'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721229911/rbrnpph9lrd5sdqwjkxt.jpg',
           25.00,'This Summer Will Be Different',7,5);
INSERT INTO books(id,description,image_url,price,title,author_id,category_id)
VALUES (8,'I thank my lucky stars that the Garricks miraculously give me a job. I can work here for a while, stay quiet until I get what I want. It’s almost perfect.',
        'https://res.cloudinary.com/dcyp9ocyf/image/upload/v1721230077/zxkgdnwiaue2twfxmnzr.jpg',
           25.00,'The housemaid’s secret',6,3);