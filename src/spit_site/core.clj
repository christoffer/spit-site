(ns spit-site.core
  (:require [clojure.data.json :as json])
  (:use [clojure.java.io]
        [spit-site.helpers]
        [clostache.parser]
        [clojure.tools.cli :only [cli]]))

(defn gather-templates
  "Gather all templates from the template resource directory."
  [template-root]
  (map
    #(assoc {} :path (path-of (relativize template-root %)) :name (.getName %))
    (filter #(has-extension? "mustache" %) (file-seq template-root))))

(defn accompaning-data
  "Look for an accompaning data (json) file to a template and return
  it's content as a map. If none is found then return an empty map"
  [tmpl data-root]
  (if (.exists (file data-root (:path tmpl)))
    (str "Existing " (.getPath (file data-root (:path tmpl))))
    (str "Non existing " (.getPath (file data-root (:path tmpl))))))
; (actualize-templates (all-templates-from (resource "templates")))

(defn spit-site
  "Generate a web site by rendering all templates in <template-dir>
  with data from corresponding files in <data-dir> (if available)"
  [template-dir data-dir]
  (let [template-root (file template-dir)
        data-root (file data-dir)]
    (println "Templates: " template-dir)
    (if-not (.exists (file template-root))
      (println "The template directory " (.getPath template-root) " does not exist"))
    (doseq [t (gather-templates template-root)]
      (println t))
    (println "Data: " data-dir)))

(defn -main [& args]
  (let [[options _ banner]
        (cli args
          ["-t" "--template-dir" "Root directory of templates" :default "./templates"]
          ["-d" "--data-dir" "Root directory of data" :default "./data"]
          ["-h" "--help" "Display this help"])]

    (when (:help options)
      (println banner)
      (System/exit 0))

    (let [{:keys [template-dir data-dir]} options]
      (spit-site template-dir data-dir))))