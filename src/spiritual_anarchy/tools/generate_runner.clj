(ns spiritual-anarchy.tools.generate-runner
  (:require [clojure.string :as str]
            [spiritual-anarchy.config :as c]
            [babashka.fs :as fs]
            [babashka.process :refer [shell]]))

(defn -main []
  (try
    (shell "git checkout generated-data")
    (catch Exception error
      (System/exit 1)))

  (prn (map str (fs/glob "feed" "**/*.json")))
  (doseq [f (fs/glob "feed" "**/*.json")]
    (fs/delete f))
  (prn (map str (fs/glob "feed" "**/*.json")))

  (c/write-json-feed c/feed)

  (doseq [[tag posts] (c/group-feed-by-tag)]
    (c/write-tag-feed tag posts))

  (doseq [[author posts] (c/group-feed-by-author)]
    (c/write-author-feed author posts))

  (shell "git" "add" "feed")
  (try
    (shell "git" "commit" "feed" "-m" "Regenerated data")
    (catch Exception error))
  (shell "git checkout master"))
