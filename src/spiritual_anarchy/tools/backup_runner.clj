(ns spiritual-anarchy.tools.backup-runner
  (:require [clojure.string :as str]
            [spiritual-anarchy.config :as c]
            [babashka.fs :as fs]
            [babashka.process :refer [shell]]
            [babashka.http-client :as http]))

;; TODO: normal page vs Reddit API
(defn -main []
  (try
    (shell "git checkout generated-data")
    (catch Exception error
      (System/exit 1)))

  (doseq [{:keys [slug link]} c/feed]
    (when-not (fs/exist? (c/archive-file slug))
      (when (c/reddit? link)
        ;; TODO: save reddit page
        ;; TODO: save non-reddit page
        )))

  (shell "git" "add" "feed/archive")
  (shell "git" "commit" "feed/archive" "-m" "Regenerated backup data")
  (shell "git checkout master"))
