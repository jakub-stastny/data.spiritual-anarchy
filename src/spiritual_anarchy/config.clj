(ns spiritual-anarchy.config
  (:require [clojure.edn :as edn]))

(def feed-path "data/feed.edn")
(def feed (edn/read-string (slurp feed-path)))

(defn add-to-feed [item]
  (spit c/feed-path (pr-str (conj feed item))))

(def json-feed-path "data/feed.json")

(defn tag-feed-path [tag]
  (str "data/tags/" tag ".json"))

(defn get-user-input [prompt]
  (print (str prompt ": ")) (flush) (read-line))
