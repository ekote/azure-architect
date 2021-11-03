import csv
import json
import os
import sys
import time
from facebook_scraper import get_posts

PAGES = ("funpage0", "funpage1") # TODO
PROJECT_NAME = "project_name" # TODO
COOCKIE_FIILE = "coockie.json" # TODO
PAGES_NUMBER = 300 # TODO

posts_file = csv.writer(open(PROJECT_NAME + "_posts.csv", "w", newline=''))
reactors_file = csv.writer(open(PROJECT_NAME + "_reactors.csv", "w", newline=''))
comments_file = csv.writer(open(PROJECT_NAME + "_comments.csv", "w", newline=''))

posts_file.writerow(
    ["id", "page", "post_id", "time", "post_url", "images_lowquality", "likes", "comments_number", "shares", "text",
     "reaction_count", "reactions_likes", "reactions_care", "reactions_haha", "reactions_wow", "reactions_love",
     "reactions_angry", "was_live"])
comments_file.writerow(
    ["id", "page", "comment_id", "comment_text", "comment_time", "comment_url", "commenter_name", "commenter_url",
     "replies_number", "post_id"])
reactors_file.writerow(["id", "link", "name", "type", "post_id"])

post_counter = 1
comments_counter = 1
reactors_counter = 1

def get_posts_with_reactions(funpage: str):
    global post_counter, reactors_counter, comments_counter
    posts = get_posts_from_fb(funpage)

    for post in posts:
        print(f">>>> Saving post for {funpage}: ID={post['post_id']}")
        print("-----------------------------------------------------------------")
        print(f"-----------------------------{post_counter}-----------------------------------")
        parsed = json.dumps(post, indent=4, sort_keys=True, default=str, ensure_ascii=False).encode('utf8')
        print(parsed.decode())
        print("-----------------------------------------------------------------")
        print("-----------------------------------------------------------------")

        if post['reactions']:
            posts_file.writerow([
                post_counter,
                funpage,
                post['post_id'],
                post['time'],
                post['post_url'],
                post['images_lowquality'],
                post['likes'],
                post['comments'],
                post['shares'],
                post['post_text'],
                post['reaction_count'],
                post['reactions']['like'] if post.get('reactions', {}).get('like') else 0,
                post['reactions']['care'] if post.get('reactions', {}).get('care') else 0,
                post['reactions']['haha'] if post.get('reactions', {}).get('haha') else 0,
                post['reactions']['wow'] if post.get('reactions', {}).get('wow') else 0,
                post['reactions']['love'] if post.get('reactions', {}).get('love') else 0,
                post['reactions']['angry'] if post.get('reactions', {}).get('angry') else 0,
                post['was_live']
            ])
        else:
            posts_file.writerow([
                post_counter,
                funpage,
                post['post_id'],
                post['time'],
                post['post_url'],
                post['images_lowquality'],
                post['likes'],
                post['comments'],
                post['shares'],
                post['post_text'],
                post['reaction_count'],
                0,
                0,
                0,
                0,
                0,
                0,
                post['was_live']
            ])

        if post['reactors']:
            for reactor in post['reactors']:
                reactors_file.writerow([
                    reactors_counter,
                    reactor['link'],
                    reactor['name'],
                    reactor['type'],
                    post['post_id']
                ])
                # print(f">>>> REACTOR NO={post_counter}")
                reactors_counter = reactors_counter + 1

        print(f">>>> POST NO={post_counter}")
        post_counter = post_counter + 1

        if post['comments'] != 0:
            print("Downloading comments...")
            org = next(get_posts(post_urls=[post["post_id"]],
                                  cookies=COOCKIE_FIILE,
                                  options={"comments": True,
                                           "reactions": True}))
            for comment in org['comments_full']:
                comments_file.writerow([
                    comments_counter,
                    funpage,
                    comment['comment_id'],
                    comment['comment_text'],
                    comment['comment_time'],
                    comment['comment_url'],
                    comment['commenter_name'],
                    comment['commenter_url'],
                    len(comment['replies']) if comment.get('replies') else 0,
                    post['post_id']
                ])
                print(f">>>> COMMENT NUMBER={comments_counter}")
                comments_counter = comments_counter + 1

        if post_counter in [200, 400, 600, 800, 1000, 1200, 1500, 1800, 2001]:
            print(">>>> Delay for a five minutes to avoid being blocked.")
            time.sleep(300)


def get_posts_from_fb(funpage: str):
    # print(f"Getting post from page {funpage}")
    return get_posts(funpage,
                          pages=PAGES_NUMBER,
                          cookies=COOCKIE_FIILE,
                          extra_info=True,
                          options={
                              "reactions": True,
                              "reactors": "generator",
                              "posts_per_page": 4000,
                              "allow_extra_requests": True
                          })

# def get_comments_from_fb(funpage):
#     # print(f"Getting comments from page {funpage}")
#     return next(get_posts(post_urls=funpage,
#                           pages=None,
#                           cookies=COOCKIE_FIILE,
#                           extra_info=True,
#                           options={
#                               "comments": 3000,
#                           }))
#
# def get_comments_for_post(funpage: str):
#     global comments_counter
#     comments = get_comments_from_fb(funpage)
#     # parsed = json.dumps(comments, indent=4, sort_keys=True, default=str, ensure_ascii=False).encode('utf8')
#     # print(parsed.decode())
#     print(f">>>> Saving comments for {funpage}: ID={comments['post_id']}")
#
#     zero_comments = not bool(comments['comments_full']) # true if there is no comment body; false if there are
#     if comments['comments'] == 0 and zero_comments:
#         print(f"Comments number and comments full equally empty")
#     elif comments['comments'] == 0 and not zero_comments:
#         print(f"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ERROR <<<<<<<<<<<<<<<<<<<<<<<<<<<")
#     else:
#         post_id = comments["post_id"]
#         for comment in comments['comments_full']:
#             comments_file.writerow([
#                 comments_counter,
#                 funpage,
#                 comment['comment_id'],
#                 comment['comment_text'],
#                 comment['comment_time'],
#                 comment['comment_url'],
#                 comment['commenter_name'],
#                 comment['commenter_url'],
#                 len(comment['replies']) if comment.get('replies') else 0,
#                 post_id
#             ])
#             print(f">>>> NO={comments_counter}")
#             comments_counter = comments_counter + 1
#
#             if comments_counter in [200, 400, 600, 800, 1000, 1200, 1500, 1800, 2001]:
#                 print(">>>> Delay for a five minutes to avoid being blocked.")
#                 time.sleep(300)

for page in PAGES:
    get_posts_with_reactions(page)
#     # get_comments_for_post(page)
