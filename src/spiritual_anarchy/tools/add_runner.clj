(ns spiritual-anarchy.tools.add-runner
  (:require [clojure.string :as str]
            [spiritual-anarchy.config :as c]))

;; TODO: generate slug from the title or sha from the Link
(defn build-map-from-cli []
  (let [title (c/get-user-input "Title")
        link (c/get-user-input "Link")
        tags (str/split (c/get-user-input "Tags") #"\s*,\s*")
        current-date (java.util.Date.)]
    {:title title :link link :date-added current-date :tags tags}))

(defn -main []
  (let [new-item (build-map-from-cli)
        updated-feed (conj c/feed new-item)]
    (c/add-to-feed new-item)
    (prn new-item)))
