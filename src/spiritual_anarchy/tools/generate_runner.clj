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

  (doseq [f (fs/glob "feed/tags" "*.json")] (fs/delete f))
  (doseq [f (fs/glob "feed/authors" "*.json")] (fs/delete f))

  (let [tags-with-posts (c/group-feed-by-tag)
        authors-with-posts (c/group-feed-by-author)]
    (c/write-post-index-feed c/feed)
    (c/write-tag-index-feed (keys tags-with-posts))
    (c/write-author-index-feed (keys authors-with-posts))

    (doseq [[tag posts] tags-with-posts]
      (c/write-tag-feed tag posts))

    (doseq [[author posts] authors-with-posts]
      (c/write-author-feed author posts)))

  (shell "git" "add" "feed")
  (try
    (shell "git" "commit" "feed" "-m" "Regenerated data")
    (catch Exception error))
  (shell "git checkout master"))
