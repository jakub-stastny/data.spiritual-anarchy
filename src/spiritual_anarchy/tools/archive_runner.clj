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

  (doseq [f (fs/glob "feed/archive" "**/*.json")] (fs/delete f))

  (doseq [{:keys [slug link]} c/feed]
    (when-not (fs/exists? (c/archive-file slug))
      (if (c/reddit? link)
        (let [response (http/get (str link ".json"))]
          (if (= (:status response) 200)
            (spit (c/archive-file slug) (:body response))
            (throw (ex-info "Request failed" {:response response}))))
        (do
          ;; TODO: save non-reddit page
          (println "TODO: Implement archiving non-reddit pages.")
          (shell "git checkout master")
          (System/exit 1)))))

  (shell "git" "add" "feed/archive")
  (shell "git" "commit" "feed/archive" "-m" "Regenerated archive data")
  (shell "git checkout master"))
