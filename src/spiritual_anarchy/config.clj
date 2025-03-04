(ns spiritual-anarchy.config
  (:require [clojure.edn :as edn]
            [fipp.edn :as fipp]))

(def feed-path "data/feed.edn")
(def feed (edn/read-string (slurp feed-path)))

(defn pretty-format [data]
  (with-out-str (fipp/pprint data)))

(defn add-to-feed [item]
  (spit feed-path (pretty-format (into [] (conj feed item)))))

(def json-feed-path "data/feed.json")

(defn tag-feed-path [tag]
  (str "data/tags/" tag ".json"))

(defn get-user-input [prompt]
  (print (str prompt ": ")) (flush) (read-line))
