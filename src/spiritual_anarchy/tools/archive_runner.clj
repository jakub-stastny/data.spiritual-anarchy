(ns spiritual-anarchy.tools.archive-runner
  (:require [clojure.string :as str]
            [spiritual-anarchy.config :as c]
            [babashka.fs :as fs]
            [babashka.process :refer [shell]]
            [babashka.http-client :as http]))

(defn -main []
  (try
    (shell "git checkout generated-data")
    (catch Exception error
      (System/exit 1)))

  (doseq [{:keys [slug link]} c/feed]
    (when-not (fs/exists? (c/archive-file slug))
      (if (c/reddit? link)
        (let [response (http/get (str link ".json"))]
          (prn response)
          (spit (c/archive-file slug) (:body response)))
        (do
          ;; TODO: save non-reddit page
          (println "TODO: Implement archiving non-reddit pages.")
          (shell "git checkout master")
          (System/exit 1)))))

  (shell "git" "add" "feed/archive")
  (shell "git" "commit" "feed/archive" "-m" "Regenerated archive data")
  (shell "git checkout master"))
