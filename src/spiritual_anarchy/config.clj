(ns spiritual-anarchy.config
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [fipp.edn :as fipp]
            [cheshire.core :as json]))

(def feed-path "data/feed.edn")
(def feed (edn/read-string (slurp feed-path)))

(defn group-feed-by-tags []
  (reduce (fn [acc {:keys [tags] :as item}]
            (reduce (fn [acc tag]
                      (update acc tag (fnil conj []) item))
                    acc tags))
          {} feed))

(defn pretty-format [data]
  (with-out-str (fipp/pprint data)))

(def format-json json/generate-string)

(defn add-to-feed [item]
  (spit feed-path (pretty-format (into [] (conj feed item)))))

(def json-feed-path "feed/posts.json")

(defn write-json-feed [feed]
  (spit json-feed-path (format-json feed)))

(defn tag-feed-path [tag]
  (str "feed/tags/" tag ".json"))

(defn write-tag-feed [tag feed]
  (spit (tag-feed-path tag) (format-json feed)))

(defn get-user-input
  ([prompt]
   (get-user-input prompt nil))

  ([prompt default-value]
   (let [label (if default-value
                 (str prompt " (" default-value ")") prompt)]

     (print (str label ": "))
     (flush))

   (let [value (read-line)]
     (if (and default-value (empty? value))
       default-value value))))

(defn archive-file [slug]
  (str "feed/archive/" slug ".json"))

(defn reddit? [link]
  (str/includes? link "reddit.com/"))
