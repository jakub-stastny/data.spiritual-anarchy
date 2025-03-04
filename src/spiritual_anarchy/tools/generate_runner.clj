(ns spiritual-anarchy.tools.generate-runner
  (:require [clojure.string :as str]
            [spiritual-anarchy.config :as c]
            [babashka.fs :as fs]
            [babashka.process :as process]))

;; TODO: Switch branches

(defn -main []
  (c/write-json-feed c/feed)

  (doseq [[tag posts] (c/group-feed-by-tags)]
    (c/write-tag-feed tag posts)))
