(ns spit-site.core
  (:require [clojure.data.json :as json])
  (:use [clojure.java.io]
        [spit-site.helpers]
        [clostache.parser]
        [clojure.tools.cli :only [cli]]))

; just a sample set of products until the data part is
; implemented
(def placeholder-products
  {:products [{:id "sku-101"
               :name "Product with all information"
               :price 19.90
               :description "This is the description"}
              {:id "sku-102"
               :name "Product without description"
               :price 19.90}
              {:name "Minimal product (only name)"}
              ]})

(defn gather-templates
  "Gather all templates from the template resource directory."
  [template-root]
  (map
    #(assoc {} :file % :relative-path (path-of (file (relativize template-root %))))
    (filter-on-extension (file-seq template-root) "mustache")))

(defn actualize-templates
  "Transform a sequence of template maps into a file structure"
  [templates]
  (doseq [tmpl templates]
    (println (str "Would create: " (tmpl :relative-path)))))

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