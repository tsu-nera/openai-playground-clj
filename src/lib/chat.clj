(ns lib.chat
  (:require
    [aleph.http :as http]
    [byte-streams :as bs]
    [cheshire.core :as json]
    [clojure.edn :as edn]
    [clojure.java.io :as io]))


;; https://platform.openai.com/docs/guides/chat
;; https://platform.openai.com/docs/api-reference/chat

(def api-key
  (delay (-> "creds.edn"
             io/resource
             slurp
             edn/read-string
             :api-key)))


(def url "https://api.openai.com/v1/chat/completions")
(def model "gpt-3.5-turbo")


(defn chat
  [content]
  (let [headers {"Authorization" (str "Bearer " @api-key)
                 "Content-Type"  "application/json"}
        data    {:model model :messages [{:role "user" :content content}]}
        body    (json/generate-string data)
        params  {:headers headers :body body}]
    (-> @(http/post url params)
        :body
        bs/to-string
        (json/parse-string true)
        :choices
        first
        (get-in [:message :content]))))


(comment
  ;;
  (def ret (chat "こんばんは！ぼくは寝たほうがいいでしょうか?"))
  (def ret2 (chat "人生は不幸の連続です."))
  ;; それは正確ではありません。人生には幸福な瞬間もあります。幸福を見つけるためには、自分自身の心、環境、自己啓発、人々との関係、機会など、さまざまな要因を考慮することが必要です。不幸な出来事が起こるかもしれませんが、それを克服することができます。人生は失敗や困難に直面することがありますが、それを乗り越えることでより強い、より愛情深い人になれます。
  )
