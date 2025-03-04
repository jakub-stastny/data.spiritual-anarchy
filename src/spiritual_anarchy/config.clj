(ns spiritual-anarchy.config
  (:require [clojure.edn :as edn]))

(def feed-path "data/feed.edn")
(def feed (edn/read-string (slurp feed-path)))

(def json-feed-path "data/feed.json")

(defn tag-feed-path [tag]
  (str "data/tags/" tag ".json"))

(defn pretty-format [data]
  (prn data))

(defn get-user-input [prompt]
  (print (str prompt ": ")) (flush) (read-line))
