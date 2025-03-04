(ns spiritual-anarchy.tools.add-runner
  (:require [clojure.string :as str]
            [spiritual-anarchy.config :as c]))

(defn build-map-from-cli []
  (let [title (c/get-user-input "Title")
        suggested-slug (str/replace (str/lower-case title) #"\s+" "-")
        slug (c/get-user-input "Slug" suggested-slug)
        link (c/get-user-input "Link")
        tags (str/split (c/get-user-input "Tags") #"\s*,\s*")
        current-date (java.util.Date.)]
    {:title title :link link :slug slug :date-added current-date :tags tags}))

(defn -main []
  (let [new-item (build-map-from-cli)]
    (c/add-to-feed new-item)
    (prn new-item)))
