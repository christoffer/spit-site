(ns spit-site.core
  (:require [clojure.data.json :as json])
  (:use [clojure.java.io]
        [spit-site.helpers]
        [clostache.parser]
        [clojure.tools.cli :only [cli]]))

(defn template-file->tmpl
  "Transform a template file pointer to a tmpl map"
  [template-root template-file]
  {:path (path-of (relativize template-root template-file))
   :name (.getName template-file)})

(defn tmpl->data-file
  "Transform a tmpl map to a data file pointer"
  [tmpl data-root]
  (let [fname (clojure.string/replace (:name tmpl) #"[.]mustache$" ".json")
        path (str (:path tmpl) java.io.File/separator fname)]
    (file data-root path)))

(defn get-templates
  "Gather all templates from the template resource directory and return them
  as a list of tmpl maps."
  [template-root]
  (map
    #(template-file->tmpl template-root %)
    (filter #(has-extension? "mustache" %) (file-seq template-root))))

(defn spit-site
  "Generate a web site by rendering all templates in <template-dir>
  with data from corresponding files in <data-dir> (where available)"
  [template-dir data-dir]
  (let [template-root (file template-dir)
        data-root (file data-dir)]
    (println "Templates: " template-dir)
    (if-not (.exists (file template-root))
      (println "The template directory\"" (.toString template-root) "\"does not exist"))
    (doseq [tmpl (get-templates template-root)]
      (println tmpl)
      (println (tmpl->data-file tmpl data-root)))))

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