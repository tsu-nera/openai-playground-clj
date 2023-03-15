#!/usr/bin/env sh

# https://qiita.com/mikito/items/b69f38c54b362c20e9e6
curl https://api.openai.com/v1/chat/completions \
-H "Authorization: Bearer $OPENAI_API_KEY" \
-H "Content-Type: application/json" \
-d '{
     "model"    : "gpt-3.5-turbo",
     "messages" : [
                   {"role" : "user", "content" : "よく使う英語のフレーズを教えて"}
                   ]
     }'
